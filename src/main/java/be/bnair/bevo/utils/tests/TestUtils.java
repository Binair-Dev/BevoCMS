package be.bnair.bevo.utils.tests;

import be.bnair.bevo.models.entities.*;
import be.bnair.bevo.models.entities.security.UserEntity;
import org.apache.catalina.Server;

import java.time.LocalDate;

public class TestUtils {
    public static UserEntity getUserEntity(long id, String name, RankEntity rank) {
        UserEntity author = new UserEntity();
        author.setId(id);
        author.setNickname(name);
        author.setEmail("Test");
        author.setPassword("Test");
        author.setConfirmed(true);
        author.setEnabled(true);
        author.setCredit(100.0);
        author.setRank(rank);
        return author;
    }

    public static RankEntity getRankEntity(long id, String rankName) {
        RankEntity rank = new RankEntity();
        rank.setId(id);
        rank.setTitle(rankName);
        rank.setPower(0L);
        return rank;
    }

    public static ShopCategoryEntity getShopCategoryEntity(long id, String shopCategoryName) {
        ShopCategoryEntity shopCategory = new ShopCategoryEntity();
        shopCategory.setId(id);
        shopCategory.setTitle(shopCategoryName);
        shopCategory.setDisplayOrder(1);
        return shopCategory;
    }

    public static NewsEntity getNewsEntity(long id, String name, UserEntity author){
        NewsEntity news = new NewsEntity();
        news.setId(id);
        news.setTitle(name);
        news.setDescription("Test");
        news.setImage("Test");
        news.setDate(LocalDate.now());
        news.setPublished(true);
        news.setAuthor(author);
        return news;
    }

    public static PaypalOfferEntity getPaypalOfferEntity(long id, String name) {
        PaypalOfferEntity offer = new PaypalOfferEntity();
        offer.setId(id);
        offer.setTitle(name);
        offer.setDescription("Test");
        offer.setPrice(1);
        offer.setCredit(10);
        return offer;
    }

    public static RuleEntity getRuleEntity(long id, String name) {
        RuleEntity rule = new RuleEntity();
        rule.setId(id);
        rule.setTitle(name);
        rule.setDescription("Test");
        return rule;
    }

    public static ServerEntity getServerEntity(long id, String name) {
        ServerEntity server = new ServerEntity();
        server.setId(id);
        server.setTitle(name);
        server.setServerIp("localhost");
        server.setServerPort(25565);
        server.setRconPort(25566);
        server.setRconPassword("Test");
        return server;
    }

    public static ShopItemEntity getShopItemEntity(long id, String name, ShopCategoryEntity shopCategory, ServerEntity server) {
        ShopItemEntity shopItemEntity = new ShopItemEntity();
        shopItemEntity.setId(id);
        shopItemEntity.setTitle(name);
        shopItemEntity.setDescription("Test");
        shopItemEntity.setImage("Test");
        shopItemEntity.setContentImage("Test");
        shopItemEntity.setPrice(1);
        shopItemEntity.setCommand("Test");
        shopItemEntity.setShopCategory(shopCategory);
        shopItemEntity.setServer(server);
        return shopItemEntity;
    }

    public static ShopTransactionEntity getShopTransactionEntity(long id, ShopItemEntity shopItemEntity, double credit, UserEntity user) {
        ShopTransactionEntity shopTransactionEntity = new ShopTransactionEntity();
        shopTransactionEntity.setId(id);
        shopTransactionEntity.setItem(shopItemEntity);
        shopTransactionEntity.setCredit(credit);
        shopTransactionEntity.setUser(user);
        return shopTransactionEntity;
    }
}
