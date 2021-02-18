package org.bahmni.reports.report;

import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.conn.PoolingClientConnectionManager;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.bahmni.webclients.AllTrustedSSLSocketFactory;
import org.bahmni.webclients.ConnectionDetails;
import org.bahmni.webclients.HttpClient;
import org.bahmni.webclients.openmrs.OpenMRSLoginAuthenticator;
import org.junit.Ignore;
import org.junit.Test;

import java.net.URI;

@Ignore
public class ReportsTest{

    @Test
    public void testReportConnection() throws Exception {
        PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager(schemeRegistry(allTrustSSLSocketFactory()));
        connectionManager.setDefaultMaxPerRoute(10);
        ConnectionDetails connectionDetails = new ConnectionDetails("http://192.168.33.10:8050/openmrs/ws/rest/v1/session",
                "superman",
                "Admin123", 30000,
                120000, connectionManager);

        HttpClient httpClient = new HttpClient(connectionDetails, new OpenMRSLoginAuthenticator(connectionDetails));
        String data=httpClient.get(new URI("https://192.168.33.10/bahmni_config/openmrs/apps/reports/reports.json"));

        System.out.println(data);
    }

    public Registry<ConnectionSocketFactory> schemeRegistry(SSLConnectionSocketFactory allTrustSSLSocketFactory){

        Registry<ConnectionSocketFactory> schemeRegistry = RegistryBuilder.<ConnectionSocketFactory>create()
                .register("https", allTrustSSLSocketFactory)
                .register("http", PlainConnectionSocketFactory.getSocketFactory())
                .build();
        return schemeRegistry;
    }

    public SSLConnectionSocketFactory allTrustSSLSocketFactory(){
        return new AllTrustedSSLSocketFactory().getSSLSocketFactory();
    }
}
