package com.sujan.uxcam.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;
import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class NetworkHelperTest {

    @Mock
    ConnectivityManager mockConnectivityManager;

    @Mock
    Context mockContext;

    @Mock
    SdkChecker mockSdkChecker;

    NetworkHelper networkHelper;

    @Mock
    NetworkInfo mockNetworkInfo;

    @Mock
    Network mockNetwork;

    @Mock
    NetworkCapabilities mockNetworkCapabilities;

    @Before
    public void setup() {
        when(mockContext.getSystemService(Context.CONNECTIVITY_SERVICE)).thenReturn(mockConnectivityManager);
        networkHelper = new NetworkHelper(mockContext, mockSdkChecker);

    }


    @Test
    public void test_no_network_connected_above_marshmallow() {
        when(mockSdkChecker.isApi23orAbove()).thenReturn(true);
        assertFalse(networkHelper.isNetworkConnected());
    }

    @Test
    public void test_has_active_network_but_no_capabilities_above_marshmallow() {
        when(mockSdkChecker.isApi23orAbove()).thenReturn(true);
        when(mockConnectivityManager.getActiveNetwork()).thenReturn(mockNetwork);
        when(mockConnectivityManager.getNetworkCapabilities(mockNetwork)).thenReturn(null);
        assertFalse(networkHelper.isNetworkConnected());
    }

    @Test
    public void test_has_different_network_above_marshmallow() {
        when(mockSdkChecker.isApi23orAbove()).thenReturn(true);
        when(mockConnectivityManager.getActiveNetwork()).thenReturn(mockNetwork);
        when(mockConnectivityManager.getNetworkCapabilities(mockNetwork)).thenReturn(mockNetworkCapabilities);
        assertFalse(networkHelper.isNetworkConnected());
    }


    @Test
    public void test_has_wifi_network_above_marshmallow() {
        when(mockSdkChecker.isApi23orAbove()).thenReturn(true);
        when(mockConnectivityManager.getActiveNetwork()).thenReturn(mockNetwork);
        when(mockConnectivityManager.getNetworkCapabilities(mockNetwork)).thenReturn(mockNetworkCapabilities);

        when(mockNetworkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)).thenReturn(true);
        assertTrue(networkHelper.isNetworkConnected());
    }

    @Test
    public void test_has_cellular_data_network_above_marshmallow() {
        when(mockSdkChecker.isApi23orAbove()).thenReturn(true);
        when(mockConnectivityManager.getActiveNetwork()).thenReturn(mockNetwork);
        when(mockConnectivityManager.getNetworkCapabilities(mockNetwork)).thenReturn(mockNetworkCapabilities);

        when(mockNetworkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)).thenReturn(true);
        assertTrue(networkHelper.isNetworkConnected());
    }

    @Test
    public void test_has_ethernet_network_above_marshmallow() {
        when(mockSdkChecker.isApi23orAbove()).thenReturn(true);
        when(mockConnectivityManager.getActiveNetwork()).thenReturn(mockNetwork);
        when(mockConnectivityManager.getNetworkCapabilities(mockNetwork)).thenReturn(mockNetworkCapabilities);

        when(mockNetworkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)).thenReturn(true);
        assertTrue(networkHelper.isNetworkConnected());
    }

    @Test
    public void test_has_vpn_network_above_marshmallow() {
        when(mockSdkChecker.isApi23orAbove()).thenReturn(true);
        when(mockConnectivityManager.getActiveNetwork()).thenReturn(mockNetwork);
        when(mockConnectivityManager.getNetworkCapabilities(mockNetwork)).thenReturn(mockNetworkCapabilities);

        when(mockNetworkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_VPN)).thenReturn(true);
        assertTrue(networkHelper.isNetworkConnected());
    }


    @Test
    public void test_no_network_connected_below_marshmallow() {
        when(mockSdkChecker.isApi23orAbove()).thenReturn(false);
        when(mockConnectivityManager.getActiveNetworkInfo()).thenReturn(null);
        assertFalse(networkHelper.isNetworkConnected());
    }

    @Test
    public void test_has_wifi_network_connected_below_marshmallow() {
        when(mockSdkChecker.isApi23orAbove()).thenReturn(false);
        when(mockConnectivityManager.getActiveNetworkInfo()).thenReturn(mockNetworkInfo);
        when(mockNetworkInfo.getType()).thenReturn(ConnectivityManager.TYPE_WIFI);
        assertTrue(networkHelper.isNetworkConnected());
    }

    @Test
    public void test_has_cellular_data_network_connected_below_marshmallow() {
        when(mockSdkChecker.isApi23orAbove()).thenReturn(false);
        when(mockConnectivityManager.getActiveNetworkInfo()).thenReturn(mockNetworkInfo);
        when(mockNetworkInfo.getType()).thenReturn(ConnectivityManager.TYPE_MOBILE);
        assertTrue(networkHelper.isNetworkConnected());
    }

    @Test
    public void test_has_ethernet_network_connected_below_marshmallow() {
        when(mockSdkChecker.isApi23orAbove()).thenReturn(false);
        when(mockConnectivityManager.getActiveNetworkInfo()).thenReturn(mockNetworkInfo);
        when(mockNetworkInfo.getType()).thenReturn(ConnectivityManager.TYPE_ETHERNET);
        assertTrue(networkHelper.isNetworkConnected());
    }

    @Test
    public void test_has_vpn_network_connected_below_marshmallow() {
        when(mockSdkChecker.isApi23orAbove()).thenReturn(false);
        when(mockConnectivityManager.getActiveNetworkInfo()).thenReturn(mockNetworkInfo);
        when(mockNetworkInfo.getType()).thenReturn(ConnectivityManager.TYPE_VPN);
        assertTrue(networkHelper.isNetworkConnected());
    }


}
