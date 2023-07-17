package io.github.yurimarx.hibernateirisdialect.function;

import static org.hibernate.query.sqm.produce.function.FunctionParameterType.DATE;
import static org.hibernate.query.sqm.produce.function.FunctionParameterType.NUMERIC;

import org.hibernate.boot.model.FunctionContributions;
import org.hibernate.dialect.function.CommonFunctionFactory;
import org.hibernate.query.sqm.function.SqmFunctionRegistry;
import org.hibernate.type.BasicType;
import org.hibernate.type.BasicTypeRegistry;
import org.hibernate.type.StandardBasicTypes;
import org.hibernate.type.spi.TypeConfiguration;

public class IRISFunctionFactory extends CommonFunctionFactory{

	private final BasicType<Double> doubleType;
	private final BasicType<String> stringType;
	private final BasicType<Integer> integerType;
	
	
	private final SqmFunctionRegistry functionRegistry;
	private final TypeConfiguration typeConfiguration;

	
	
	public IRISFunctionFactory(FunctionContributions functionContributions) {
		super(functionContributions);
		functionRegistry = functionContributions.getFunctionRegistry();
		typeConfiguration = functionContributions.getTypeConfiguration();

		BasicTypeRegistry basicTypeRegistry = typeConfiguration.getBasicTypeRegistry();
		doubleType = basicTypeRegistry.resolve(StandardBasicTypes.DOUBLE);
		stringType = basicTypeRegistry.resolve(StandardBasicTypes.STRING);
		integerType = basicTypeRegistry.resolve(StandardBasicTypes.INTEGER);
		
	}
	
	@Override
	public void cot() {
		
		functionRegistry.patternDescriptorBuilder( "cot", "{fn cot(?1)}" )
			.setInvariantType(doubleType)
			.setExactArgumentCount( 1 )
			.setParameterTypes(NUMERIC)
			.register();
	}
	
	public void exp() {
		
		functionRegistry.patternDescriptorBuilder( "exp", "{fn exp(?1)}" )
			.setInvariantType(doubleType)
			.setExactArgumentCount( 1 )
			.setParameterTypes(NUMERIC)
			.register();
	}
	
	public void sinCosTanAtan2() {
		
		functionRegistry.patternDescriptorBuilder( "sin", "{fn sin(?1)}" )
			.setInvariantType(doubleType)
			.setExactArgumentCount( 1 )
			.setParameterTypes(NUMERIC)
			.register();
		
		functionRegistry.patternDescriptorBuilder( "cos", "{fn cos(?1)}" )
			.setInvariantType(doubleType)
			.setExactArgumentCount( 1 )
			.setParameterTypes(NUMERIC)
			.register();
			
		functionRegistry.patternDescriptorBuilder( "tan", "{fn tan(?1)}" )
			.setInvariantType(doubleType)
			.setExactArgumentCount( 1 )
			.setParameterTypes(NUMERIC)
			.register();
		
		functionRegistry.patternDescriptorBuilder( "atan2", "{fn atan2(?1,?2)}" )
			.setInvariantType(doubleType)
			.setExactArgumentCount( 2 )
			.setParameterTypes(NUMERIC, NUMERIC)
			.register();
	}
	
	@Override
	public void pi() {
		functionRegistry.patternDescriptorBuilder( "pi", "{fn pi()}" )
			.setInvariantType(doubleType)
			.setExactArgumentCount(0)
			.setArgumentListSignature("")
			.register();
	}
	
	@Override
	public void daynameMonthname() {
				
		functionRegistry.patternDescriptorBuilder( "monthname", "{fn monthname(?1)}" )
			.setInvariantType(stringType)
			.setExactArgumentCount( 1 )
			.setParameterTypes(DATE)
			.register();
	
		functionRegistry.patternDescriptorBuilder( "dayname", "{fn dayname(?1)}" )
			.setInvariantType(stringType)
			.setExactArgumentCount( 1 )
			.setParameterTypes(DATE)
			.register();

	}
	
	@Override
	public void dayofweekmonthyear() {
		functionRegistry.patternDescriptorBuilder( "dayofweek", "{fn dayofweek(?1)}" )
				.setInvariantType(integerType)
				.setExactArgumentCount( 1 )
				.setParameterTypes(DATE)
				.register();
		functionRegistry.patternDescriptorBuilder( "dayofmonth", "{fn dayofmonth(?1)}" )
				.setInvariantType(integerType)
				.setExactArgumentCount( 1 )
				.setParameterTypes(DATE)
				.register();
		functionRegistry.patternDescriptorBuilder( "dayofyear", "{fn dayofyear(?1)}" )
				.setInvariantType(integerType)
				.setExactArgumentCount( 1 )
				.setParameterTypes(DATE)
				.register();
		functionRegistry.registerAlternateKey( "day", "dayofmonth" );
		
	}
	
}
