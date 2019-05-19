package mx.config;

import java.io.Serializable;

import org.springframework.stereotype.Component;

@Component
public interface Parametros extends Serializable {
	
	public static final String CONFIG_PROPERTIES="mx/gob/sep/config/config.properties";
	
	public static final String WS_CT_AUT_USER="DGTIC";
	public static final String WS_CT_AUT_PASS="C0nsult4s2017";
	
	
}
