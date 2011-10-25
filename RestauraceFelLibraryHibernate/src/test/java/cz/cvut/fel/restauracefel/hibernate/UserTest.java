/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.cvut.fel.restauracefel.hibernate;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Vizi
 */
public class UserTest {

    public UserTest() {
        User user1 = new User();
        User user2 = new User(null, null, null, null, null, 0);

        assertFalse( user1 ==  null );
        assertTrue( user1 instanceof User );

        assertFalse( user2 ==  null );
        assertTrue( user2 instanceof User );
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

    @Test
    public void testGetUserId() {
        User user = new User(null, null, null, null, null, 0);

        int ID = 76543;

        user.setUserId(ID);

        assertFalse( ID != user.getUserId( ) );

    }

    @Test
    public void testSetUserId() {

        User user = new User(null, null, null, null, null, 0);

        int ID = 76543;

        user.setUserId(ID);

        assertFalse( ID != user.getUserId( ) );
    }

    @Test
    public void testGetFirstName() {
        User user = new User(null, null, null, null, null, 0);

        String test = "TEST089!( ** @ˇ^˘°°˛``˙\\n";

        user.setFirstName(test);
        assertEquals(test, user.getFirstName() );
    }

    @Test
    public void testSetFirstName() {
        User user = new User(null, null, null, null, null, 0);

        String test = "TEST089!( ** @ˇ^˘°°˛``˙\\n";

        user.setFirstName(test);
        assertEquals(test, user.getFirstName() );
    }

    @Test
    public void testGetLastName() {
        User user = new User(null, null, null, null, null, 0);

        String test = "TEST089!( ** @ˇ^˘°°˛``˙\\n";

        user.setLastName(test);
        assertEquals(test, user.getLastName() );
    }

    @Test
    public void testSetLastName() {
        User user = new User(null, null, null, null, null, 0);

        String test = "TEST089!( ** @ˇ^˘°°˛``˙\\n";

        user.setLastName(test);
        assertEquals(test, user.getLastName() );
    }

    @Test
    public void testGetPersonalIdentificationNumber() {
        User user = new User(null, null, null, null, null, 0);

        String test = "TEST089!( ** @ˇ^˘°°˛``˙\\n";

        user.setPersonalIdentificationNumber(test);
        assertEquals(test, user.getPersonalIdentificationNumber() );
    }

    @Test
    public void testSetPersonalIdentificationNumber() {
        User user = new User(null, null, null, null, null, 0);

        String test = "TEST089!( ** @ˇ^˘°°˛``˙\\n";

        user.setPersonalIdentificationNumber(test);
        assertEquals(test, user.getPersonalIdentificationNumber() );
    }

    @Test
    public void testGetUsername() {
        User user = new User(null, null, null, null, null, 0);

        String test = "TEST089!( ** @ˇ^˘°°˛``˙\\n";

        user.setUsername(test);
        assertEquals(test, user.getUsername() );
    }

    @Test
    public void testSetUsername() {
        User user = new User(null, null, null, null, null, 0);

        String test = "TEST089!( ** @ˇ^˘°°˛``˙\\n";

        user.setUsername(test);
        assertEquals(test, user.getUsername() );
    }

    @Test
    public void testGetPassword() {
        User user = new User(null, null, null, null, null, 0);

        String test = "TEST089!( ** @ˇ^˘°°˛``˙\\n";

        user.setPassword(test);
        assertEquals(test, user.getPassword( ) );
    }

    @Test
    public void testSetPassword() {
        User user = new User(null, null, null, null, null, 0);

        String test = "TEST089!( ** @ˇ^˘°°˛``˙\\n";

        user.setPassword(test);
        assertEquals(test, user.getPassword( ) );
    }

    @Test
    public void testGetIsDeleted() {
        User user = new User(null, null, null, null, null, 0);

        int test = 76543;

        user.setIsDeleted(test);

        assertFalse( test != user.getIsDeleted( ) );
    }

    @Test
    public void testSetIsDeleted() {
        User user = new User(null, null, null, null, null, 0);

        int test = 76543;

        user.setIsDeleted(test);

        assertFalse( test != user.getIsDeleted( ) );
    }

}