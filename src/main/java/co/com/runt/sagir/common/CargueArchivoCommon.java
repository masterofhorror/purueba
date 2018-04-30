/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.runt.sagir.common;

/**
 *
 * @author dsalamanca
 */
public class CargueArchivoCommon {
     
    public static final String EXPRESION_REGULAR_NOMBRE_ARCHIVO_PROPIETARIOS = "([0-9]{7,8})|mprop.dat.p7z";
    public static final String EXPRESION_REGULAR_NOMBRE_ARCHIVO_REPOTENCIACION = "^[0-9]{7,8}(repot.dat)|^[0-9]{7,8}(repot.DAT)|^[0-9]{7,8}(REPOT.dat)|^[0-9]{7,8}(REPOT.DAT)|\\.p7z";
    public static final String PATH_ARCHIVO = "C:\\Archivos";
    public static final String EXPRESION_NOMBRE_ARCHIVO_LICENCIAS = "^[0-9]{7,8}(mlice.dat)|^[0-9]{7,8}(mlice.DAT)|^[0-9]{7,8}(MLICE.dat)|^[0-9]{7,8}(MLICE.DAT)|\\\\.p7z";
    public static final String EXPRESION_NOMBRE_ARCHIVO_RESIDENCIAS = "^[0-9]{7,8}(mresi.dat)|^[0-9]{7,8}(mresi.DAT)|^[0-9]{7,8}(MRESI.dat)|^[0-9]{7,8}(MRESI.DAT)|\\\\.p7z";
    public static final String EXPRESION_NOMBRE_ARCHIVO_PERSONAS = "^[0-9]{7,8}(mpers.dat)|^[0-9]{7,8}(mpers.DAT)|^[0-9]{7,8}(MPERS.dat)|^[0-9]{7,8}(MPERS.DAT)|\\\\.p7z";
    public static final String EXPRESION_NOMBRE_ARCHIVO_RESTRICCIONES = "^[0-9]{7,8}(mresl.dat)|^[0-9]{7,8}(mresl.DAT)|^[0-9]{7,8}(MRESL.dat)|^[0-9]{7,8}(MRESL.DAT)|\\\\.p7z";
}
