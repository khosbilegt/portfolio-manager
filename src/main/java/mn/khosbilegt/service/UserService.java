package mn.khosbilegt.service;

import at.favre.lib.crypto.bcrypt.BCrypt;
import io.quarkus.security.UnauthorizedException;
import io.smallrye.jwt.build.Jwt;
import io.smallrye.mutiny.Uni;
import io.smallrye.mutiny.unchecked.Unchecked;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.NotFoundException;
import mn.khosbilegt.jooq.generated.tables.records.PfUserRecord;
import mn.khosbilegt.service.user.UserDTO;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.jooq.DSLContext;

import static mn.khosbilegt.jooq.generated.Tables.PF_USER;

@ApplicationScoped
public class UserService {
    @Inject
    DSLContext context;
    @ConfigProperty(name = "mn.khosbilegt.jwt.issuer", defaultValue = "https://khosbilegt.dev")
    String jwtIssuer;

    private String generateJWT(String userId, String role) {
        return Jwt.issuer(jwtIssuer)
                .upn(userId)
                .groups(role)
                .sign();
    }

    public Uni<String> createUser(UserDTO user) {
        return Uni.createFrom().voidItem()
                .map(Unchecked.function(unused -> {
                    String encryptedPassword = BCrypt.withDefaults().hashToString(12, user.getPassword().toCharArray());
                    PfUserRecord userRecord = new PfUserRecord();
                    userRecord.setEmail(user.getEmail());
                    userRecord.setUsername(user.getUsername());
                    userRecord.setPassword(encryptedPassword);
                    userRecord.setType(user.getType());
                    userRecord.setRole("user");
                    PfUserRecord insertedRecord = context.insertInto(PF_USER)
                            .set(userRecord)
                            .returning()
                            .fetchOne();
                    if (insertedRecord != null) {
                        return generateJWT(String.valueOf(insertedRecord.getUserId()), insertedRecord.getRole());
                    }
                    throw new RuntimeException("Failed to create user");
                }));
    }

    public Uni<String> loginUser(UserDTO userDTO) {
        return Uni.createFrom().voidItem()
                .map(Unchecked.function(unused -> {
                    PfUserRecord userRecord = context.selectFrom(PF_USER)
                            .where(PF_USER.EMAIL.eq(userDTO.getEmail()))
                            .fetchOne();
                    if (userRecord != null) {
                        if (BCrypt.verifyer().verify(userDTO.getPassword().toCharArray(), userRecord.getPassword()).verified) {
                            return generateJWT(String.valueOf(userRecord.getUserId()), userRecord.getRole());
                        } else {
                            throw new RuntimeException("Invalid password");
                        }
                    } else {
                        throw new NotFoundException("User not found");
                    }
                }));
    }

    public void updateUser(String userId, UserDTO user) {
        PfUserRecord userRecord = context.selectFrom(PF_USER)
                .where(PF_USER.USER_ID.eq(Integer.parseInt(userId)))
                .fetchOne();
        if (userRecord != null) {
            userRecord.setEmail(user.getEmail());
            userRecord.setUsername(user.getUsername());
            userRecord.setType(user.getType());
            userRecord.setRole("user");
            PfUserRecord updatedRecord = context.update(PF_USER)
                    .set(userRecord)
                    .returning()
                    .fetchOne();
            if (updatedRecord == null) {
                throw new RuntimeException("Failed to update user");
            }
        } else {
            throw new RuntimeException("User not found");
        }
    }

    public void deleteUser(String userId) {
        PfUserRecord userRecord = context.selectFrom(PF_USER)
                .where(PF_USER.USER_ID.eq(Integer.parseInt(userId)))
                .fetchOne();
        if (userRecord != null) {
            context.deleteFrom(PF_USER)
                    .where(PF_USER.USER_ID.eq(Integer.parseInt(userId)))
                    .execute();
        } else {
            throw new NotFoundException("User not found");
        }
    }

}
