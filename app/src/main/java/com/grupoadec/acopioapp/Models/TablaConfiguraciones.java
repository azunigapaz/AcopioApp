package com.grupoadec.acopioapp.Models;

public class TablaConfiguraciones {
    private String ConfiguracionId;
    private String ConfiguracionSufijoDocumento;
    private Integer ConfiguracionUltimoDocumento;
    private String ConfiguracionUrl;
    private String ConfiguracionTipoImpresora;

    public TablaConfiguraciones(String configuracionId, String configuracionSufijoDocumento, Integer configuracionUltimoDocumento, String configuracionUrl, String configuracionTipoImpresora) {
        ConfiguracionId = configuracionId;
        ConfiguracionSufijoDocumento = configuracionSufijoDocumento;
        ConfiguracionUltimoDocumento = configuracionUltimoDocumento;
        ConfiguracionUrl = configuracionUrl;
        ConfiguracionTipoImpresora = configuracionTipoImpresora;
    }

    public TablaConfiguraciones(){};

    public String getConfiguracionId() {return ConfiguracionId;}

    public void setConfiguracionId(String configuracionId) {ConfiguracionId = configuracionId;}

    public String getConfiguracionSufijoDocumento() {return ConfiguracionSufijoDocumento;}

    public void setConfiguracionSufijoDocumento(String configuracionSufijoDocumento) {ConfiguracionSufijoDocumento = configuracionSufijoDocumento;}

    public Integer getConfiguracionUltimoDocumento() {return ConfiguracionUltimoDocumento;}

    public void setConfiguracionUltimoDocumento(Integer configuracionUltimoDocumento) {ConfiguracionUltimoDocumento = configuracionUltimoDocumento;}

    public String getConfiguracionUrl() {return ConfiguracionUrl;}

    public void setConfiguracionUrl(String configuracionUrl) {ConfiguracionUrl = configuracionUrl;}

    public String getConfiguracionTipoImpresora() {return ConfiguracionTipoImpresora;}

    public void setConfiguracionTipoImpresora(String configuracionTipoImpresora) {ConfiguracionTipoImpresora = configuracionTipoImpresora;}
}
