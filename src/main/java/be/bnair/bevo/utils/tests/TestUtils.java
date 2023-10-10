package be.bnair.bevo.utils.tests;

import be.bnair.bevo.models.entities.NewsEntity;
import be.bnair.bevo.models.entities.RankEntity;
import be.bnair.bevo.models.entities.ShopCategoryEntity;
import be.bnair.bevo.models.entities.security.UserEntity;

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
        return null;
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
}
