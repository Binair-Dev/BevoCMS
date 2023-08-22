package be.bnair.bevo.utils;

import be.bnair.bevo.models.entities.NewsEntity;
import be.bnair.bevo.models.entities.PaypalOfferEntity;
import be.bnair.bevo.models.entities.RankEntity;
import be.bnair.bevo.models.entities.RuleEntity;
import be.bnair.bevo.models.entities.ServerEntity;
import be.bnair.bevo.models.entities.ShopCategoryEntity;
import be.bnair.bevo.models.entities.ShopItemEntity;
import be.bnair.bevo.models.entities.VoteEntity;
import be.bnair.bevo.models.entities.VoteRewardEntity;
import be.bnair.bevo.models.entities.WikiEntity;
import be.bnair.bevo.repository.NewsRepository;
import be.bnair.bevo.repository.PaypalOfferRepository;
import be.bnair.bevo.repository.RankRepository;
import be.bnair.bevo.repository.RuleRepository;
import be.bnair.bevo.repository.ServerRepository;
import be.bnair.bevo.repository.ShopCategoryRepository;
import be.bnair.bevo.repository.ShopItemRepository;
import be.bnair.bevo.repository.WikiRepository;

import java.time.LocalDate;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.ServerRedirectStrategy;
import org.springframework.stereotype.Component;

import be.bnair.bevo.models.entities.security.UserEntity;
import be.bnair.bevo.models.enums.EnumRewardType;
import be.bnair.bevo.repository.UserRepository;
import be.bnair.bevo.repository.VoteRepository;
import be.bnair.bevo.repository.VoteRewardRepository;

@Component
public class DataInit implements InitializingBean {

    private final UserRepository userRepository;
    private final RankRepository rankRepository;
    private final WikiRepository wikiRepository;
    private final NewsRepository newsRepository;
    private final PaypalOfferRepository paypalOfferRepository;
    private final RuleRepository ruleRepository;
    private final VoteRewardRepository voteRewardRepository;
    private final ServerRepository serverRepository;
    private final PasswordEncoder passwordEncoder;
    private final VoteRepository voteRepository;
    private final ShopCategoryRepository shopCategoryRepository;
    private final ShopItemRepository shopItemRepository;

    public DataInit(UserRepository userRepository, RankRepository rankRepository, WikiRepository wikiRepository,
    NewsRepository newsRepository, PaypalOfferRepository paypalOfferRepository, RuleRepository ruleRepository, VoteRewardRepository voteRewardRepository,
                   ServerRepository serverRepository, PasswordEncoder passwordEncoder, VoteRepository voteRepository, ShopCategoryRepository shopCategoryRepository,
                   ShopItemRepository shopItemRepository) {
        this.userRepository = userRepository;
        this.rankRepository = rankRepository;
        this.wikiRepository = wikiRepository;
        this.newsRepository = newsRepository;
        this.paypalOfferRepository = paypalOfferRepository;
        this.ruleRepository = ruleRepository;
        this.voteRewardRepository = voteRewardRepository;
        this.serverRepository = serverRepository;
        this.passwordEncoder = passwordEncoder;
        this.voteRepository = voteRepository;
        this.shopCategoryRepository = shopCategoryRepository;
        this.shopItemRepository = shopItemRepository;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        //region Création des Rangs
        RankEntity member = new RankEntity();
        member.setTitle("Membre");
        member.setPower(1L);
        rankRepository.save(member);

        RankEntity admin = new RankEntity();
        admin.setTitle("Administrateur");
        admin.setPower(100L);
        rankRepository.save(admin);
        //endregion
        //region Création des Utilisateurs
        UserEntity b_nair = new UserEntity();
        b_nair.setNickname("b_nair");
        b_nair.setEmail("van.bellinghen.brian@gmail.com");
        b_nair.setPassword(passwordEncoder.encode("test1234@"));
        b_nair.setConfirmed(true);
        b_nair.setEnabled(true);
        b_nair.setRank(admin);
        userRepository.save(b_nair);

        UserEntity julie = new UserEntity();
        julie.setNickname("Julie");
        julie.setEmail("frazelle.julie@gmail.com");
        julie.setPassword(passwordEncoder.encode("disneytapeur"));
        julie.setConfirmed(true);
        julie.setEnabled(true);
        julie.setRank(member);
        userRepository.save(julie);
        //endregion
        //region Création des News
        NewsEntity news1 = new NewsEntity();
        news1.setTitle("News 1");
        news1.setDescription("Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incid");
        news1.setImage("https://geo.img.pmdstatic.net/scale/https.3A.2F.2Fi.2Epmdstatic.2Enet.2Fgeo.2F2022.2F08.2F29.2F78a8d08d-5002-46e1-bc28-5e4a7bdb9ded.2Ejpeg/autox450/quality/65/nasique-2-2.jpg");
        news1.setDate(LocalDate.now());
        news1.setPublished(true);
        news1.setAuthor(b_nair);
        newsRepository.save(news1);
        //endregion
        //region Création des Wiki
        WikiEntity wiki1 = new WikiEntity();
        wiki1.setTitle("Titre 1 (Singe)");
        wiki1.setDescription("Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam.");
        wiki1.setImage("https://geo.img.pmdstatic.net/scale/https.3A.2F.2Fi.2Epmdstatic.2Enet.2Fgeo.2F2022.2F08.2F29.2F78a8d08d-5002-46e1-bc28-5e4a7bdb9ded.2Ejpeg/autox450/quality/65/nasique-2-2.jpg");
        wikiRepository.save(wiki1);

        WikiEntity wiki2 = new WikiEntity();
        wiki2.setTitle("Titre 2");
        wiki2.setDescription("Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam.");
        wiki2.setImage("https://www.cleverfiles.com/howto/wp-content/uploads/2018/03/minion.jpg");
        wikiRepository.save(wiki2);
        //endregion
        //region Création des Offres Paypal
        PaypalOfferEntity paypalOffer1 = new PaypalOfferEntity();
        paypalOffer1.setTitle("5e");
        paypalOffer1.setDescription("5e vous donneront 500 credits");
        paypalOffer1.setPrice(5);
        paypalOffer1.setCredit(500);
        paypalOfferRepository.save(paypalOffer1);

        PaypalOfferEntity paypalOffer2 = new PaypalOfferEntity();
        paypalOffer2.setTitle("10e");
        paypalOffer2.setDescription("10e vous donneront 1000 credits");
        paypalOffer2.setPrice(10);
        paypalOffer2.setCredit(1000);
        paypalOfferRepository.save(paypalOffer2);
        //endregion
        //region Création des Règles
        RuleEntity rule1 = new RuleEntity();
        rule1.setTitle("Ne pas faire d'attaque DDOS envers notre infrastructure");
        rule1.setDescription("Une attaque DDoS, ou par d\u00E9ni de service distribu\u00E9, est un type de cyberattaque qui tente de rendre un site Web ou une ressource r\u00E9seau indisponible en l'inondant de trafic malveillant afin de l'emp\u00EAcher de fonctionner.");
        ruleRepository.save(rule1);
        //endregion
        //region Creations des serveurs
        ServerEntity server1 = new ServerEntity();
        server1.setTitle("Serveur 1");
        server1.setServerIp("127.0.0.1");
        server1.setServerPort(25565);
        server1.setRconPassword("test1234@");
        server1.setRconPort(25566);
        serverRepository.save(server1);
        //endregion
        //region Creations des récompenses de votes
        VoteRewardEntity voteRewardEntity = new VoteRewardEntity();
        voteRewardEntity.setTitle("R\u00E9compense de votes 1");
        voteRewardEntity.setPercent(100);
        voteRewardEntity.setRewardType(EnumRewardType.COMMAND);
        voteRewardEntity.setCommand("/give %player% apple 1");
        voteRewardEntity.setCredit(0);
        voteRewardEntity.setServer(server1);
        voteRewardRepository.save(voteRewardEntity);
        //endregion
        //region Creations votes
        VoteEntity vote = new VoteEntity();
        vote.setDate(LocalDate.now());
        vote.setUser(julie);
        voteRepository.save(vote);
        //endregion
        //region Creations ShopCategory
        ShopCategoryEntity shopCategoryEntity = new ShopCategoryEntity();
        shopCategoryEntity.setTitle("Shop Category");
        shopCategoryEntity.setDisplayOrder(1);
        shopCategoryRepository.save(shopCategoryEntity);
        //endregion
        //region Creations ShopItem
        ShopItemEntity shopItemEntity = new ShopItemEntity();
        shopItemEntity.setTitle("Shop item test");
        shopItemEntity.setDescription("Description banale");
        shopItemEntity.setImage("https://geo.img.pmdstatic.net/scale/https.3A.2F.2Fi.2Epmdstatic.2Enet.2Fgeo.2F2022.2F08.2F29.2F78a8d08d-5002-46e1-bc28-5e4a7bdb9ded.2Ejpeg/autox450/quality/65/nasique-2-2.jpg");
        shopItemEntity.setPrice(20.0);
        shopItemEntity.setCommand("/give %player% apple 1");
        shopItemEntity.setShopCategory(shopCategoryEntity);
        shopItemEntity.setServer(server1);
        shopItemRepository.save(shopItemEntity);
        //endregion
    }
}