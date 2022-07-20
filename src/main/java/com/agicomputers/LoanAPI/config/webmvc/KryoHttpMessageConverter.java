package com.agicomputers.LoanAPI.config.webmvc;

import com.agicomputers.LoanAPI.models.dto.AppUserDTO;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.AbstractHttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;

public class KryoHttpMessageConverter extends AbstractHttpMessageConverter<Object> {
    public static final MediaType KRYO = new MediaType("application","x-kryo");
    public static final ThreadLocal<Kryo> kryoThreadLocal = new ThreadLocal<>(){
        @Override
        protected Kryo initialValue(){
            Kryo kryo = new Kryo();
            kryo.register(BufferedImage.class,1);
            return kryo;
        }
    };
    public KryoHttpMessageConverter(){
        super(KRYO);
    }
    @Override
    protected boolean supports(Class<?> clazz) {
       return Object.class.isAssignableFrom(clazz);
    }

    @Override
    protected Object readInternal(Class<?> clazz, HttpInputMessage inputMessage) throws IOException, HttpMessageNotReadableException {
        Input input = new Input(inputMessage.getBody());
        return kryoThreadLocal.get().readClassAndObject(input);
    }

    @Override
    protected void writeInternal(Object o, HttpOutputMessage outputMessage) throws IOException, HttpMessageNotWritableException {
        Output output = new Output(outputMessage.getBody());
        kryoThreadLocal.get().writeClassAndObject(output, o);
        output.flush();
    }

    @Override
    public List<MediaType> getSupportedMediaTypes(Class<?> clazz) {
        return List.of(KRYO,MediaType.IMAGE_JPEG);
    }
}
