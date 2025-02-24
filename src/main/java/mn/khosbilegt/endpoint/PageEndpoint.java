package mn.khosbilegt.endpoint;

import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.SecurityContext;
import mn.khosbilegt.service.PageService;
import mn.khosbilegt.service.page.Block;
import mn.khosbilegt.service.page.Page;
import mn.khosbilegt.service.page.Tag;
import org.eclipse.microprofile.jwt.JsonWebToken;

import java.util.Collection;

@Path("/api/page")
public class PageEndpoint {
    @Inject
    PageService pageService;
    @Inject
    JsonWebToken jwt;

    @GET
    @Path("/")
    public Collection<Page> fetchPages() {
        return pageService.fetchPages();
    }

    @GET
    @Path("/{id}")
    public Page fetchPage(@PathParam("id") int id) {
        return pageService.fetchPage(id);
    }

    @POST
    @RolesAllowed({"admin"})
    public Page createPage(Page page) {
        return pageService.createPage(page);
    }

    @PATCH
    @Path("/{id}")
    @RolesAllowed({"admin"})
    public Page updatePage(@PathParam("id") int id, Page page) {
        return pageService.updatePage(id, page);
    }

    @PUT
    @Path("/{pageId}/tag/{tagId}")
    @RolesAllowed({"admin"})
    public Page addTagToPage(@PathParam("pageId") int pageId, @PathParam("tagId") int tagId) {
        return pageService.addTagToPage(pageId, tagId);
    }

    @DELETE
    @Path("/{pageId}/tag/{tagId}")
    @RolesAllowed({"admin"})
    public Page removeTagFromPage(@PathParam("pageId") int pageId, @PathParam("tagId") int tagId) {
        return pageService.removeTagFromPage(pageId, tagId);
    }

    @DELETE
    @Path("/{id}")
    @RolesAllowed({"admin"})
    public void deletePage(@PathParam("id") int id) {
        pageService.deletePage(id);
    }

    @GET
    @Path("/tags")
    public Collection<Tag> fetchTags(@QueryParam("type") @DefaultValue("") String type, @QueryParam("name") @DefaultValue("") String name) {
        return pageService.fetchTags(name, type);
    }

    @GET
    @Path("/tags/{id}")
    public Tag fetchTag(@PathParam("id") int id) {
        return pageService.fetchTag(id);
    }

    @POST
    @Path("/tags")
//    @RolesAllowed({"admin"})
    public Tag createTag(@Context SecurityContext ctx, Tag tag) {
        System.out.println(getResponseString(ctx));
//        return pageService.createTag(tag);
        return new Tag();
    }

    private String getResponseString(SecurityContext ctx) {
        String name;
        if (ctx.getUserPrincipal() == null) {
            name = "anonymous";
        } else if (!ctx.getUserPrincipal().getName().equals(jwt.getName())) {
            throw new InternalServerErrorException("Principal and JsonWebToken names do not match");
        } else {
            name = ctx.getUserPrincipal().getName();
        }
        return String.format("hello %s,"
                        + " isHttps: %s,"
                        + " authScheme: %s,"
                        + " hasJWT: %s",
                name, ctx.isSecure(), ctx.getAuthenticationScheme(), jwt.getClaimNames() != null);
    }

    @PATCH
    @Path("/tags/{id}")
    @RolesAllowed({"admin"})
    public Tag updateTag(@PathParam("id") int id, Tag tag) {
        return pageService.updateTag(id, tag);
    }

    @DELETE
    @Path("/tags/{id}")
    @RolesAllowed({"admin"})
    public void deleteTag(@PathParam("id") int id) {
        pageService.deleteTag(id);
    }

    @GET
    @Path("/tags/{id}/pages")
    public Collection<Page> fetchPagesByTag(@PathParam("id") int id) {
        return pageService.fetchPagesByTag(id);
    }

    @GET
    @Path("/block")
    public Collection<Block> fetchBlocks() {
        return pageService.fetchBlocks();
    }

    @GET
    @Path("/block/{id}")
    public Block fetchBlock(@PathParam("id") int id) {
        return pageService.fetchBlock(id);
    }

    @POST
    @Path("/block")
    @RolesAllowed({"admin"})
    public Block createBlock(Block block) {
        return pageService.createBlock(block);
    }

    @PATCH
    @Path("/block/{id}")
    @RolesAllowed({"admin"})
    public Block updateBlock(@PathParam("id") int id, Block block) {
        return pageService.updateBlock(id, block);
    }

    @DELETE
    @Path("/block/{id}")
    @RolesAllowed({"admin"})
    public void deleteBlock(@PathParam("id") int id) {
        pageService.deleteBlock(id);
    }
}
