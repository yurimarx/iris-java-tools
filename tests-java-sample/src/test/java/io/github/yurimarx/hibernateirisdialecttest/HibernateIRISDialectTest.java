package io.github.yurimarx.hibernateirisdialecttest;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import io.github.yurimarx.hibernateiristest.model.Product;
import io.github.yurimarx.hibernateiristest.model.ProductCategory;
import io.github.yurimarx.hibernateiristest.util.HibernateUtil;

@TestMethodOrder(OrderAnnotation.class)
class HibernateIRISDialectTest {

	private static SessionFactory sessionFactory;
	private Session session;

	@BeforeAll
	public static void setup() {
		sessionFactory = HibernateUtil.getSessionFactory();
		System.out.println("SessionFactory created");
	}

	@AfterAll
	public static void tearDown() {
		if (sessionFactory != null)
			sessionFactory.close();
		System.out.println("SessionFactory destroyed");
	}

	@BeforeEach
	public void openSession() {
		session = sessionFactory.openSession();
		System.out.println("Session created");
	}

	@AfterEach
	public void closeSession() {
		if (session != null)
			session.close();
		System.out.println("Session closed\n");
	}
	
	@Test
	@Order(1)
	public void testBatchCreate() {
		System.out.println("Running testBatchCreate...");
		
		session.beginTransaction();
		
		for (int i = 0; i < 10; i++) {
			ProductCategory cat = new ProductCategory();
			cat.setName("Cat " + i);
			cat.setDescription("Cat " + i);
			cat = session.merge(cat);
			
		}
		
		session.getTransaction().commit();
		
		Query<ProductCategory> query = session.createQuery("from ProductCategory", ProductCategory.class);
		List<ProductCategory> resultList = query.getResultList();

		Assertions.assertEquals(resultList.size(), 10);

	}

	@Test
	@Order(2)
	public void testCreate() {
		System.out.println("Running testCreate...");

		session.beginTransaction();
		Product product = null;
		
		try {
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
			dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
			Date parsedDate = dateFormat.parse("2023-01-01 12:13:14.000");
		    
			product = new Product("Product 1", "Product 1", 1.0, 2.0, 3.0, parsedDate);
			ProductCategory cat = session.find(ProductCategory.class, 1);
			product.setProductCategory(cat);
			product = session.merge(product);
			
			session.getTransaction().commit();
		} catch (Exception e) {
			session.getTransaction().rollback();
		}

		Assertions.assertTrue(product.getId() > 0);
	}

	@Test
	@Order(3)
	public void testGet() {
		System.out.println("Running testGet...");

		Integer id = 1;

		Product product = session.find(Product.class, id);

		assertEquals("Product 1", product.getName());
	}

	@Test
	@Order(4)
	public void testGetNotExist() {
		System.out.println("Running testGetNotExist...");

		Integer id = 10;

		Product product = session.find(Product.class, id);

		Assertions.assertNull(product);
	}

	@Test
	@Order(5)
	public void testList() {
		System.out.println("Running testList...");

		Query<Product> query = session.createQuery("from Product", Product.class);
		List<Product> resultList = query.getResultList();

		Assertions.assertFalse(resultList.isEmpty());
	}
	
	@Test
	@Order(6)
	public void testFunctions() {
		System.out.println("Running testFunctions...");

		try {
			Query<Object[]> query = session.createQuery("select avg(height), min(height), max(height) from Product", Object[].class);
			Object[] result = query.getSingleResult();
					
			Query<Object[]> query2 = session.createQuery("select trim(' Inter Systems '), "
					+ "substring('InterSystems', 1, 5), "
					+ "'Inter'||'Systems', "
					+ " cot(1.57), "
					+ " acos(0.52), "
					+ " log(10.0), "
					+ " pi(), "
					+ " space(3) || 'spaces', "
					+ " extract(second from p.releaseDate), "
					+ " extract(minute from p.releaseDate), "
					+ " extract(hour from p.releaseDate), "
					+ " extract(day from p.releaseDate), "
					+ " extract(week from p.releaseDate), "
					+ " extract(quarter from p.releaseDate), "
					+ " extract(month from p.releaseDate), "
					+ " extract(year from p.releaseDate), "
					+ " monthname(current_date()), "
					+ " dayname(current_date()), "
					+ " to_number('10'), "
					+ " to_char(13), "
					+ " to_date('2023-01-01','YYYY-MM-DD'), "
					+ " length('Brazil'),"
					+ " truncate(12.2222,0), "
					+ " dayofweek(now), "
					+ " dayofmonth(curdate), "
					+ " dayofyear(current_date()), "
					+ " replicate('hello',2), "
					+ " datepart(month, p.releaseDate), "
					+ " ascii('a'), "
					+ " char(97), "
					+ " extract(minute from curtime), "
					+ " extract(minute from sysdate), "
					+ " stddev(20), "
					+ " stddev_pop(20), "
					+ " stddev_samp(20), "
					+ " variance(20), "
					+ " var_pop(20), "
					+ " var_samp(20), "
					+ " last_day(sysdate), "
					+ " locate('I','InterSystems'), "
					+ " sin(2), "
					+ " cos(2), "
					+ " tan(2), "
					+ " asin(0.52), "
					+ " atan(0.52), "
					+ " atan2(15,30), "
					+ " exp(1), "
					+ " left('InterSystems',5), "
					+ " right('InterSystems',7) "
					+ " from Product p", Object[].class);
			Object[] result2 = query2.getSingleResult();
			
			Assertions.assertEquals((Double)result[0], 1.0);
			Assertions.assertEquals((Double)result[1], 1.0);
			Assertions.assertEquals((Double)result[2], 1.0);
			
			Assertions.assertEquals((String)result2[0], "Inter Systems");
			Assertions.assertEquals((String)result2[1], "Inter");
			Assertions.assertEquals((String)result2[2], "InterSystems");
			Assertions.assertEquals((Double)result2[3], 7.963269632232547E-4);
			Assertions.assertEquals((Float)result2[4], Float.valueOf("1.0239453"));
			Assertions.assertEquals((Float)result2[5], Float.valueOf("2.3025851"));
			Assertions.assertEquals((Double)result2[6], Double.valueOf(3.141592653589793));
			Assertions.assertEquals((String)result2[7], "   spaces");
			Assertions.assertEquals((Float)result2[8], Float.valueOf("14.0"));
			Assertions.assertEquals(result2[9],13);
			Assertions.assertEquals(result2[10],12);
			Assertions.assertEquals(result2[11],1);
			Assertions.assertEquals(result2[12],1);
			Assertions.assertEquals(result2[13],1);
			Assertions.assertEquals(result2[14],1);
			Assertions.assertEquals(result2[15],2023);
			Calendar date = Calendar.getInstance();
			Assertions.assertEquals(result2[16], date.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.US));
			Assertions.assertEquals(result2[17], date.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.US));
			Assertions.assertEquals(result2[18],10.0);
			Assertions.assertEquals(result2[19],"13");
			Assertions.assertEquals(result2[20].toString(),"2023-01-01");
			Assertions.assertEquals(result2[21],6);
			Assertions.assertEquals((Float)result2[22],Float.valueOf("12.0"));
			Assertions.assertTrue((Integer)result2[23] > 0);
			Assertions.assertTrue((Integer)result2[24] > 0);
			Assertions.assertTrue((Integer)result2[25] > 0);
			Assertions.assertEquals(result2[26], "hellohello");
			Assertions.assertEquals((Long)result2[27], Long.valueOf(1));
			Assertions.assertEquals((Integer)result2[28], Integer.valueOf(97));
			Assertions.assertEquals((Character)result2[29], 'a');
			Assertions.assertTrue((Integer)result2[30] > -1);
			Assertions.assertTrue((Integer)result2[31] > -1);
			Assertions.assertEquals((Double)result2[32],Double.valueOf("0.0"));
			Assertions.assertEquals((Double)result2[33],Double.valueOf("0.0"));
			Assertions.assertNull(result2[34]);
			Assertions.assertEquals((Double)result2[35],Double.valueOf("0.0"));
			Assertions.assertEquals((Double)result2[36],Double.valueOf("0.0"));
			Assertions.assertNull(result2[37]);
			Assertions.assertNotNull(result2[38]);
			Assertions.assertTrue((Integer)result2[39] > -1);
			Assertions.assertEquals((Double)result2[40],Double.valueOf("0.9092974268256817"));
			Assertions.assertEquals((Double)result2[41],Double.valueOf("-0.4161468365471424"));
			Assertions.assertEquals((Double)result2[42],Double.valueOf("-2.185039863261519"));
			Assertions.assertEquals((Float)result2[43],Float.valueOf("0.546851"));
			Assertions.assertEquals((Float)result2[44],Float.valueOf("0.47951928"));
			Assertions.assertEquals((Double)result2[45],Double.valueOf("0.4636476090008061"));
			Assertions.assertEquals((Double)result2[46],Double.valueOf("2.718281828459045"));
			Assertions.assertEquals(result2[47],"Inter");
			Assertions.assertEquals(result2[48],"Systems");
			System.out.print("OLHA AI:" + result2[16]);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	@Order(7)
	public void testLazyLoad() {
		System.out.println("Running lazyLoad...");

		Integer id = 1;
		ProductCategory cat = session.find(ProductCategory.class, id);

		Assertions.assertNotNull(cat.getProducts());
	}
	
	@Test
	@Order(8)
	public void testDelete() {
		System.out.println("Running testDelete...");

		Integer id = 1;
		Product product = session.find(Product.class, id);

		session.beginTransaction();
		session.remove(product);
		session.getTransaction().commit();

		Product deletedProduct = session.find(Product.class, id);

		Assertions.assertNull(deletedProduct);
	}
}
