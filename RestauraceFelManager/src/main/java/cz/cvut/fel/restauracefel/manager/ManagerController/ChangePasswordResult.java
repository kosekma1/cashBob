/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.cvut.fel.restauracefel.manager.ManagerController;

/**
 *
 * @author honza
 */
public enum ChangePasswordResult {
    ChangePasswordSuccesful, ChangePasswordFail, WrongOldPassword, EmptyOldPassword, InvalidNewPassword, NewPasswordsNotMatch, ConnectionFail, ConfigFileNotFound
}
