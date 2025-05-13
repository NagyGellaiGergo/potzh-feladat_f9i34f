package hu.pte.mik.prog4.potzh.jaas;

import at.favre.lib.crypto.bcrypt.BCrypt;
import hu.pte.mik.prog4.potzh.entity.RoleEntity;
import hu.pte.mik.prog4.potzh.entity.UserEntity;
import hu.pte.mik.prog4.potzh.repository.RoleRepository;
import hu.pte.mik.prog4.potzh.repository.UserRepository;

import javax.security.auth.Subject;
import javax.security.auth.callback.*;
import javax.security.auth.login.LoginException;
import javax.security.auth.spi.LoginModule;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class AuthenticationModule implements LoginModule {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    private CallbackHandler callbackHandler;
    private String login;
    private List<String> userGroups;
    private Subject subject;

    public AuthenticationModule() {
        this.roleRepository = new RoleRepository();
        this.userRepository = new UserRepository();
    }

    @Override
    public void initialize(Subject subject,
                           CallbackHandler callbackHandler,
                           Map<String, ?> sharedState,
                           Map<String, ?> options) {
        this.subject = subject;
        this.callbackHandler = callbackHandler;
    }

    @Override
    public boolean login() throws LoginException {
        try {
            Callback[] callbacks = new Callback[2];
            callbacks[0] = new NameCallback("login");
            callbacks[1] = new PasswordCallback("password", true);

            this.callbackHandler.handle(callbacks);
            String name = ((NameCallback) callbacks[0]).getName();
            String password = String.valueOf(((PasswordCallback) callbacks[1]).getPassword());

            if(name !=  null) {
                UserEntity user = this.userRepository.findByUsername(name);
                BCrypt.Result verify = BCrypt.verifyer().verify(password.toCharArray(), user.getPassword());

                if(verify.verified) {
                    this.login = name;
                    this.userGroups = this.roleRepository.findRolesByUser(user.getId())
                            .stream()
                            .map(RoleEntity::getCode)
                            .collect(Collectors.toList());
                    return true;
                }
            }

            throw new LoginException("Authentication failed!");
        } catch (IOException | UnsupportedCallbackException e) {
            throw new LoginException("Excepion: " + e.getMessage());
        }
    }

    @Override
    public boolean commit() throws LoginException {
        subject.getPrincipals().add(new UserPrincipal(login));
        userGroups.forEach(role -> {
            subject.getPrincipals().add(new RolePrincipal(role));
        });

        return true;
    }


    @Override
    public boolean abort() throws LoginException {
        return false;
    }

    @Override
    public boolean logout() throws LoginException {
        this.subject.getPrincipals().clear();
        return true;
    }
}
