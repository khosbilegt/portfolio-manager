package mn.khosbilegt.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.jooq.DSLContext;

@ApplicationScoped
public class PageService {
    @Inject
    DSLContext context;

    public void createBlog() {}
}
