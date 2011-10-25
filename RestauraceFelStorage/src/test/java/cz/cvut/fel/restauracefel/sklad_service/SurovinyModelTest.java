/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.cvut.fel.restauracefel.sklad_service;

import java.util.ArrayList;
import cz.cvut.fel.restauracefel.hibernate.Material;
import java.util.List;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Vizi
 */
public class SurovinyModelTest {

     //= null ;

    public SurovinyModelTest( ) {
        /*List<Material> materials = new ArrayList<Material>( );
        SurovinyModel model = new SurovinyModel( materials );
        assertFalse( model == null );*/
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() {
    }

    // TODO test nefunguje
    @Test
    public void testGetSuroviny( ) {
        /*List<Material> materials = new ArrayList<Material>( );
        SurovinyModel model = new SurovinyModel( materials );

        Material material = new Material( );
        materials.add( material );

        model.suroviny = materials;
        
        assertEquals( model.getSuroviny( ), materials );*/
    }

    /*@Test
    public void testGetSize( ) {

        for ( int i = 0; i < 3; i++ )
        {
            List<Material> materials = new ArrayList<Material>( );
            SurovinyModel model = new SurovinyModel( materials );
            
            int temp = ( int )( Math.random( ) * 5 );
            for ( int j = 0; j < temp; j++ ) {
                Material material = new Material( );
                model.suroviny.add( material );
            }

            assertFalse( model.getSize( ) != temp );
        }

    }

    @Test
    public void testGetElementAt( ) {
        List<Material> materials = new ArrayList<Material>( );
        SurovinyModel model = new SurovinyModel( materials );

        Material material0 = new Material( );
        Material material1 = new Material( );
        Material material2 = new Material( );

        material0.setName( "material0" );
        material1.setName( "material1" );
        material2.setName( "material2" );

        model.suroviny.add( material0 );
        model.suroviny.add( material1 );
        model.suroviny.add( 2, material2 );

        assertEquals( material0.getName( ), model.getElementAt( 0 ) );
        assertEquals( material1.getName( ), model.getElementAt( 1 ) );
        assertEquals( material2.getName( ), model.getElementAt( 2 ) );
    }

    @Test
    public void testGetMaterialAt() {
        List<Material> materials = new ArrayList<Material>( );
        SurovinyModel model = new SurovinyModel( materials );

        Material material0 = new Material( );
        Material material1 = new Material( );
        Material material2 = new Material( );

        material0.setName( "material0" );
        material1.setName( "material1" );
        material2.setName( "material2" );

        model.suroviny.add( material0 );
        model.suroviny.add( material1 );
        model.suroviny.add( 2, material2 );

        assertEquals( material0, model.getMaterialAt( 0 ) );
        assertEquals( material1, model.getMaterialAt( 1 ) );
        assertEquals( material2, model.getMaterialAt( 2 ) );
    }

    @Test
    public void testRemoveElement() {
                List<Material> materials = new ArrayList<Material>( );
        SurovinyModel model = new SurovinyModel( materials );

        Material material0 = new Material( );
        Material material1 = new Material( );
        Material material2 = new Material( );

        model.suroviny.add( material0 );
        model.suroviny.add( material1 );
        model.suroviny.add( 2, material2 );

        for (int i = 2; i >= 0; i--) {
            model.removeElement( i );
            assertFalse( model.getSize( ) != i );
        }
    }*/

}