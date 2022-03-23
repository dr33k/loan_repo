package com.agicomputers.LoanAPI.models.generators;
/*
import java.util.Properties;
import java.util.stream.Stream;
import org.hibernate.HibernateException;
import org.hibernate.MappingException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.spi.Configurable;
import org.hibernate.type.Type;
import org.springframework.beans.ConfigurablePropertyAccessor;

import java.io.Serializable;

public class CustomerGenerator implements Configurable, IdentifierGenerator {
  private String prefix;
    @Override
    public Serializable generate(SharedSessionContractImplementor session,Object obj) throws HibernateException{

       String query =  String.format("select %s from %s", session.getEntityPersister(obj.getClass().getName(), obj)
                .getIdentifierPropertyName(),
                obj.getClass().getSimpleName());

       Stream ids = session.createQuery(query).stream();

       Long max = ids.map(o -> o.replace(prefix,""))
               .mapToLong(x-> Long.parseLong((String)x))
               .max()
               .orElse(0L);

    return prefix+(max+1);
    }

    @Override
    public void configure(Type type, Properties properties, ServiceRegistry serviceRegistry)throws MappingException{
        prefix = properties.getProperty("prefix");
    }
}

 */