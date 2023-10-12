package be.bnair.bevo.utils.tests;

import be.bnair.bevo.models.entities.*;
import be.bnair.bevo.models.entities.security.UserEntity;
import be.bnair.bevo.models.enums.EnumRewardType;
import org.apache.catalina.Server;

import java.time.LocalDate;
import java.time.Period;

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
        author.setCreatedAt(LocalDate.now());
        author.setUpdatedAt(LocalDate.now());
        return author;
    }

    public static UserEntity getDisabledUserEntity(long id, String name, RankEntity rank) {
        UserEntity author = new UserEntity();
        author.setId(id);
        author.setNickname(name);
        author.setEmail("Test");
        author.setPassword("Test");
        author.setConfirmed(true);
        author.setEnabled(false);
        author.setCredit(100.0);
        author.setRank(rank);
        author.setCreatedAt(LocalDate.now());
        author.setUpdatedAt(LocalDate.now());
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

    public static TransactionEntity getTransactionEntity(long id, String name, UserEntity user) {
        TransactionEntity transaction = new TransactionEntity();
        transaction.setId(id);
        transaction.setCredit(1);
        transaction.setPrice(1);
        transaction.setDate(LocalDate.now());
        transaction.setUser(user);
        transaction.setIdentifier(name);
        return transaction;
    }

    public static VoteRewardEntity getVoteRewardEntity(long id, String name, ServerEntity server) {
        VoteRewardEntity voteReward = new VoteRewardEntity();
        voteReward.setId(id);
        voteReward.setTitle(name);
        voteReward.setPercent(1);
        voteReward.setRewardType(EnumRewardType.CREDIT);
        voteReward.setCommand("Test");
        voteReward.setCredit(1);
        voteReward.setServer(server);
        return voteReward;
    }

    public static VoteEntity getVoteEntity(long id, UserEntity user) {
        VoteEntity vote = new VoteEntity();
        vote.setId(id);
        vote.setDate(LocalDate.now());
        vote.setUser(user);
        return vote;
    }

    public static WikiEntity getWikiEntity(long id, String name) {
        WikiEntity wikiEntity = new WikiEntity();
        wikiEntity.setId(id);
        wikiEntity.setTitle(name);
        wikiEntity.setDescription("Test");
        wikiEntity.setImage("Test");
        return wikiEntity;
    }
}
