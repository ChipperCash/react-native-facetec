package com.reactnativefacetec.ZoomProcessors;

import com.facetec.zoom.sdk.ZoomCustomization;

public class ZoomGlobalState {
    // Replace string below with your license key from https://dev.zoomlogin.com/zoomsdk/#/account
    public static String DeviceLicenseKeyIdentifier = "dwXrwVA2ar5iw0wT2KWLCQHkq2ekyMK4";

    // "https://api.zoomauth.com/api/v2/biometrics" for FaceTec Managed Testing API.
    // "http://localhost:8080" if running ZoOm Server SDK (Dockerized) locally.
    // Otherwise, your webservice URL.
    public static String ZoomServerBaseURL = "https://api.zoomauth.com/api/v2/biometrics";

    // The customer-controlled public key used during encryption of FaceMap data.
    // Please see https://dev.zoomlogin.com/zoomsdk/#/licensing-and-encryption-keys for more information.

    // OUR (CHIPPER) KEY
    public static String PublicFaceMapEncryptionKey = 
        "-----BEGIN PUBLIC KEY-----\n" +
        "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAv+HDfn7oE26J3g9LRS4D\n" +
        "FtFiXMigTUarU1GZi8Q487KHxb4mxlPIroY0nOerMZRZr6T6ltKwRO6n6Izld4Ji\n" +
        "ukjaSF6UcWI+i7NXHqyumO8VcReJscSoV/aGaQNBbenh6seX5+XmOE5erlAw1QqQ\n" +
        "5Nmji/MSMrNXbWKgSWYYkwzDyth9Yi6KQX2vuhebcduPGnqr3Mi9YXOCtUgAJ83A\n" +
        "qx0dcmwZ74ysfaUsgoKbRDQxT96Ff0nto6CfXExRG4J9EtQLJS0E4E6ztl0ElwOH\n" +
        "iNTG3LF46T8PH64rBllt/ejDVZlQIOeRUjNBjnfJ3+0IkBlLLCMmTtvgCJ5kPg3k\n" +
        "twIDAQAB\n" +
        "-----END PUBLIC KEY-----";

    public static String LicenseText = 
        "appId      = com.chippercash\n" +
        "expiryDate = 2020-11-10\n" +
        "key        = 00304502200297458d060622f0be36dc78a86a3428370081250b6bfcf5e1013477f0562608022100e84bfb972bab94042ddd840bf6beafc187cb37cc7eb2473739cafee661ef6684";

    // Used for bookkeeping around demonstrating enrollment/authentication functionality of ZoOm.
    public static String randomUsername = "";
    public static boolean isRandomUsernameEnrolled = false;

    // This app can modify the customization to demonstrate different look/feel preferences for ZoOm.
    public static ZoomCustomization currentCustomization = new ZoomCustomization();
}
