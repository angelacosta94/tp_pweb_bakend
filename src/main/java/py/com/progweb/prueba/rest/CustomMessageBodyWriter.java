package py.com.progweb.prueba.rest;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyWriter;
import javax.ws.rs.ext.Provider;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

@Provider
public class CustomMessageBodyWriter implements MessageBodyWriter<ByteArrayOutputStream> {

    @Override
    public boolean isWriteable(Class<?> type, Type genericType,
                               Annotation[] annotations, MediaType mediaType) {
        return type == ByteArrayOutputStream.class && (
                mediaType.toString().equals("application/pdf") ||
                mediaType.toString().equals("application/vnd.ms-excel")
        );
    }

    @Override
    public long getSize(ByteArrayOutputStream baos, Class<?> type, Type genericType,
                        Annotation[] annotations, MediaType mediaType) {
        // deprecated by JAX-RS 2.0 and ignored by Jersey runtime
        return baos.size();
    }

    @Override
    public void writeTo(ByteArrayOutputStream baos, Class<?> type, Type genericType, Annotation[] annotations,
                        MediaType mediaType, MultivaluedMap<String, Object> httpHeaders,
                        OutputStream out) throws IOException, WebApplicationException {

        out.write(baos.toByteArray());
    }
}