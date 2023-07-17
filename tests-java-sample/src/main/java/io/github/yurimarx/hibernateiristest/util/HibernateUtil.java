package io.github.yurimarx.hibernateiristest.util;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import io.github.yurimarx.hibernateiristest.model.Product;
import io.github.yurimarx.hibernateiristest.model.ProductCategory;

public class HibernateUtil {
	public static SessionFactory getSessionFactory() {
		SessionFactory factory = null;
		try {
			Configuration configuration = new Configuration()
					.configure()
					.addAnnotatedClass(ProductCategory.class)
					.addAnnotatedClass(Product.class);

			factory = configuration.buildSessionFactory();

		} catch (Exception e) {
			e.printStackTrace();
		}

		return factory;
	}
}
