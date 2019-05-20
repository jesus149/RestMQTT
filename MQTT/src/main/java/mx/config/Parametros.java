package mx.config;

import java.io.Serializable;

import org.springframework.stereotype.Component;

@Component
public interface Parametros extends Serializable {
	
	public static final String CONFIG_PROPERTIES="mx/gob/sep/config/config.properties";
	
	
}
