package mn.khosbilegt.endpoint;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import mn.khosbilegt.service.PageService;
import mn.khosbilegt.service.page.Block;
import mn.khosbilegt.service.page.Page;
import mn.khosbilegt.service.page.Tag;

import java.util.Collection;

@Path("/api/page")
public class PageEndpoint {
    @Inject
    PageService pageService;

    @GET
    public Collection<Page> fetchPages() {
        return pageService.fetchPages();
    }

    @GET
    @Path("/{id}")
    public Page fetchPage(@PathParam("id") int id) {
        return pageService.fetchPage(id);
    }

    @POST
    public Page createPage(Page page) {
        return pageService.createPage(page);
    }

    @PATCH
    @Path("/{id}")
    public Page updatePage(@PathParam("id") int id, Page page) {
        return pageService.updatePage(id, page);
    }

    @DELETE
    @Path("/{id}")
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
    public Tag createTag(Tag tag) {
        return pageService.createTag(tag);
    }

    @PATCH
    @Path("/tags/{id}")
    public Tag updateTag(@PathParam("id") int id, Tag tag) {
        return pageService.updateTag(id, tag);
    }

    @DELETE
    @Path("/tags/{id}")
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
    public Block createBlock(Block block) {
        return pageService.createBlock(block);
    }

    @PATCH
    @Path("/block/{id}")
    public Block updateBlock(@PathParam("id") int id, Block block) {
        return pageService.updateBlock(id, block);
    }

    @DELETE
    @Path("/block/{id}")
    public void deleteBlock(@PathParam("id") int id) {
        pageService.deleteBlock(id);
    }
}
