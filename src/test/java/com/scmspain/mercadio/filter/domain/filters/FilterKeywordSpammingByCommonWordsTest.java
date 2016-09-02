package com.scmspain.mercadio.filter.domain.filters;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class FilterKeywordSpammingByCommonWordsTest {
    private Filter sut;

    @Before
    public void setUp() throws Exception {
        sut = new FilterKeywordSpammingByCommonWords();
    }

    @Test
    public void shouldNotDeleteAnything() throws Exception {
        final String input = getAllPrepositionsAsString();
        final String expected = getAllPrepositionsAsString();

        testExample(input, expected);
    }

    @Test
    public void shouldDeleteAll() throws Exception {
        final String input = getKeyWordSpamWithoutPrepositions();
        final String expected = "";

        testExample(input, expected);
    }

    private void testExample(String input, String expected) {
        final String result = sut.filter(input);

        Assert.assertEquals(expected,result);
    }

    private static String getAllPrepositionsAsString() {
        return "a ,  ante ,  bajo ,  cabe ,  con ,  contra ,  de ,  desde ,  en ,  entre ,  hacia ,  hasta ,  para ,  por ,  según ,  segun ,  sin ,  so ,  sobre ,  tras";
    }

    private static String getKeyWordSpamWithoutPrepositions() {
        return "apple iphone 4 4s 5 5c 5s 6 plus ipad ipod air macbook pro\n" +
                "imac 2 3 samsung galaxy s2 s3 s4 s5 s6 note edge lg g3 g2 sony xperia z1 z2 z3\n" +
                "tablet tab htc one plus blackberry nokia lumia nexus motorola mini ps4 ps3 psp\n" +
                "xbox hp toshiba lenovo m9 m8 xiaomi mi mi4 acer vaio huawei p7 p8 zte lite\n" +
                "alienware msi ultrabook notebook alcatel ps3 juegos i3 i7 g x tv smart go cámara\n" +
                "icloud smartphone android monster beats gear reloj pulsera wear watch ordenador\n" +
                "sobremesa bq wii nintendo canon nikon 6s \\nmovil mobil chino libre libres chinos comprar barato baratos smarphone smarfone\n" +
                "Smartphone dualsim duales 0 4.7 4 4.0 celular moviles celulares bq tablet pulgadas\n" +
                "\" 5.7\" 5.5\" 4.7\" 4.5\" 5\" mobile movile phone kayac cayak cayac canoa embarcacion\n" +
                "zodiak zodiac neumatica barca surf \\n edge bq xperia sony htc lg note 4 samsumg galaxy s6 ,galaxy s5 galaxy s4 galaxy\n" +
                "s3 galaxi galaxia note 5, galaxia samsum Samsun sansung jiayu replica clon iPhone\n" +
                "lenovo hdc ,huawei huawey z3 apple z2 z1 z5 z6, g3 g4 z4, samsung, aifon, comprar \\n\\n";
    }
}
