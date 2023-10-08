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

/**
 * Classe d'initialisation des données pour l'application Bevo.
 * Cette classe se charge de créer et d'initialiser les données de base nécessaires au fonctionnement de l'application.
 *
 * @author Brian Van Bellinghen
 */
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

    /**
     * Constructeur de la classe DataInit.
     *
     * @param userRepository       Le référentiel des utilisateurs.
     * @param rankRepository       Le référentiel des rangs.
     * @param wikiRepository       Le référentiel des pages Wiki.
     * @param newsRepository       Le référentiel des actualités.
     * @param paypalOfferRepository Le référentiel des offres Paypal.
     * @param ruleRepository       Le référentiel des règles.
     * @param voteRewardRepository Le référentiel des récompenses de vote.
     * @param serverRepository     Le référentiel des serveurs.
     * @param passwordEncoder      L'encodeur de mots de passe.
     * @param voteRepository       Le référentiel des votes.
     * @param shopCategoryRepository Le référentiel des catégories de la boutique.
     * @param shopItemRepository   Le référentiel des articles de la boutique.
     */
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

    /**
     * Méthode d'initialisation des données appelée après l'injection de dépendances.
     *
     * @throws Exception En cas d'erreur lors de l'initialisation des données.
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        //region Création des Rangs
        RankEntity admin = new RankEntity();
        admin.setTitle("Administrateur");
        admin.setPower(100L);
        rankRepository.save(admin);

        RankEntity member = new RankEntity();
        member.setTitle("Membre");
        member.setPower(1L);
        rankRepository.save(member);

        //endregion
        //region Création des Utilisateurs
        UserEntity b_nair = new UserEntity();
        b_nair.setNickname("B_nair");
        b_nair.setEmail("van.bellinghen.brian@gmail.com");
        b_nair.setPassword(passwordEncoder.encode("test1234@"));
        b_nair.setConfirmed(true);
        b_nair.setEnabled(true);
        b_nair.setRank(admin);
        b_nair.setCredit(1500);
        userRepository.save(b_nair);

        UserEntity julie = new UserEntity();
        julie.setNickname("B_test");
        julie.setEmail("B_test@gmail.com");
        julie.setPassword(passwordEncoder.encode("test1234@"));
        julie.setConfirmed(true);
        julie.setEnabled(true);
        julie.setRank(member);
        julie.setCredit(50);
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

        NewsEntity news2 = new NewsEntity();
        news2.setTitle("News 2");
        news2.setDescription("Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incid");
        news2.setImage("https://geo.img.pmdstatic.net/scale/https.3A.2F.2Fi.2Epmdstatic.2Enet.2Fgeo.2F2022.2F08.2F29.2F78a8d08d-5002-46e1-bc28-5e4a7bdb9ded.2Ejpeg/autox450/quality/65/nasique-2-2.jpg");
        news2.setDate(LocalDate.now());
        news2.setPublished(true);
        news2.setAuthor(b_nair);
        newsRepository.save(news2);

        NewsEntity news3 = new NewsEntity();
        news3.setTitle("News 3");
        news3.setDescription("Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incid");
        news3.setImage("https://geo.img.pmdstatic.net/scale/https.3A.2F.2Fi.2Epmdstatic.2Enet.2Fgeo.2F2022.2F08.2F29.2F78a8d08d-5002-46e1-bc28-5e4a7bdb9ded.2Ejpeg/autox450/quality/65/nasique-2-2.jpg");
        news3.setDate(LocalDate.now());
        news3.setPublished(true);
        news3.setAuthor(b_nair);
        newsRepository.save(news3);

        NewsEntity news4 = new NewsEntity();
        news4.setTitle("News 4");
        news4.setDescription("Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incid");
        news4.setImage("https://geo.img.pmdstatic.net/scale/https.3A.2F.2Fi.2Epmdstatic.2Enet.2Fgeo.2F2022.2F08.2F29.2F78a8d08d-5002-46e1-bc28-5e4a7bdb9ded.2Ejpeg/autox450/quality/65/nasique-2-2.jpg");
        news4.setDate(LocalDate.now());
        news4.setPublished(true);
        news4.setAuthor(b_nair);
        newsRepository.save(news4);
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
        server1.setTitle("Utopia - Local");
        server1.setServerIp("localhost");
        server1.setServerPort(25565);
        server1.setRconPassword("test1234");
        server1.setRconPort(25575);
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
        VoteEntity vote1 = new VoteEntity();
        vote1.setDate(LocalDate.now());
        vote1.setUser(julie);
        voteRepository.save(vote1);
        VoteEntity vote2 = new VoteEntity();
        vote2.setDate(LocalDate.now());
        vote2.setUser(julie);
        voteRepository.save(vote2);
        VoteEntity vote3 = new VoteEntity();
        vote3.setDate(LocalDate.now());
        vote3.setUser(julie);
        voteRepository.save(vote3);
        VoteEntity vote4 = new VoteEntity();
        vote4.setDate(LocalDate.now());
        vote4.setUser(b_nair);
        voteRepository.save(vote4);
        VoteEntity vote5 = new VoteEntity();
        vote5.setDate(LocalDate.now());
        vote5.setUser(b_nair);
        voteRepository.save(vote5);
        //endregion
        //region Creations ShopCategory
        ShopCategoryEntity shopCategoryTest = new ShopCategoryEntity();
        shopCategoryTest.setTitle("Test");
        shopCategoryTest.setDisplayOrder(1);
        shopCategoryRepository.save(shopCategoryTest);
        ShopCategoryEntity shopCategoryGrade = new ShopCategoryEntity();
        shopCategoryGrade.setTitle("Grades");
        shopCategoryGrade.setDisplayOrder(2);
        shopCategoryRepository.save(shopCategoryGrade);
        ShopCategoryEntity shopCategoryKits = new ShopCategoryEntity();
        shopCategoryKits.setTitle("Kit");
        shopCategoryKits.setDisplayOrder(3);
        shopCategoryRepository.save(shopCategoryKits);
        //endregion
        //region Creations ShopItem
        ShopItemEntity shopItemTestDay = new ShopItemEntity();
        shopItemTestDay.setTitle("Day");
        shopItemTestDay.setDescription("Permet de mettre le jour");
        shopItemTestDay.setImage("https://geo.img.pmdstatic.net/scale/https.3A.2F.2Fi.2Epmdstatic.2Enet.2Fgeo.2F2022.2F08.2F29.2F78a8d08d-5002-46e1-bc28-5e4a7bdb9ded.2Ejpeg/autox450/quality/65/nasique-2-2.jpg");
        shopItemTestDay.setContentImage("https://geo.img.pmdstatic.net/scale/https.3A.2F.2Fi.2Epmdstatic.2Enet.2Fgeo.2F2022.2F08.2F29.2F78a8d08d-5002-46e1-bc28-5e4a7bdb9ded.2Ejpeg/autox450/quality/65/nasique-2-2.jpg");
        shopItemTestDay.setPrice(10.0);
        shopItemTestDay.setCommand("/time set 0");
        shopItemTestDay.setShopCategory(shopCategoryTest);
        shopItemTestDay.setServer(server1);
        shopItemRepository.save(shopItemTestDay);

        ShopItemEntity shopItemTestNight = new ShopItemEntity();
        shopItemTestNight.setTitle("Night");
        shopItemTestNight.setDescription("Permet de mettre la nuit");
        shopItemTestNight.setImage("https://geo.img.pmdstatic.net/scale/https.3A.2F.2Fi.2Epmdstatic.2Enet.2Fgeo.2F2022.2F08.2F29.2F78a8d08d-5002-46e1-bc28-5e4a7bdb9ded.2Ejpeg/autox450/quality/65/nasique-2-2.jpg");
        shopItemTestNight.setContentImage("https://geo.img.pmdstatic.net/scale/https.3A.2F.2Fi.2Epmdstatic.2Enet.2Fgeo.2F2022.2F08.2F29.2F78a8d08d-5002-46e1-bc28-5e4a7bdb9ded.2Ejpeg/autox450/quality/65/nasique-2-2.jpg");
        shopItemTestNight.setPrice(10.0);
        shopItemTestNight.setCommand("/time set 0");
        shopItemTestNight.setShopCategory(shopCategoryTest);
        shopItemTestNight.setServer(server1);
        shopItemRepository.save(shopItemTestNight);

        ShopItemEntity shopItemEntity = new ShopItemEntity();
        shopItemEntity.setTitle("Kit alchimiste");
        shopItemEntity.setDescription("Description banale");
        shopItemEntity.setImage("https://geo.img.pmdstatic.net/scale/https.3A.2F.2Fi.2Epmdstatic.2Enet.2Fgeo.2F2022.2F08.2F29.2F78a8d08d-5002-46e1-bc28-5e4a7bdb9ded.2Ejpeg/autox450/quality/65/nasique-2-2.jpg");
        shopItemEntity.setContentImage("https://geo.img.pmdstatic.net/scale/https.3A.2F.2Fi.2Epmdstatic.2Enet.2Fgeo.2F2022.2F08.2F29.2F78a8d08d-5002-46e1-bc28-5e4a7bdb9ded.2Ejpeg/autox450/quality/65/nasique-2-2.jpg");
        shopItemEntity.setPrice(20.0);
        shopItemEntity.setCommand("/give %player% apple 1");
        shopItemEntity.setShopCategory(shopCategoryKits);
        shopItemEntity.setServer(server1);
        shopItemRepository.save(shopItemEntity);

        ShopItemEntity shopItemGradeEmpereur = new ShopItemEntity();
        shopItemGradeEmpereur.setTitle("Grade Empereur");
        shopItemGradeEmpereur.setDescription("Description banale");
        shopItemGradeEmpereur.setImage("https://geo.img.pmdstatic.net/scale/https.3A.2F.2Fi.2Epmdstatic.2Enet.2Fgeo.2F2022.2F08.2F29.2F78a8d08d-5002-46e1-bc28-5e4a7bdb9ded.2Ejpeg/autox450/quality/65/nasique-2-2.jpg");
        shopItemGradeEmpereur.setContentImage("https://geo.img.pmdstatic.net/scale/https.3A.2F.2Fi.2Epmdstatic.2Enet.2Fgeo.2F2022.2F08.2F29.2F78a8d08d-5002-46e1-bc28-5e4a7bdb9ded.2Ejpeg/autox450/quality/65/nasique-2-2.jpg");
        shopItemGradeEmpereur.setPrice(20.0);
        shopItemGradeEmpereur.setCommand("/give %player% apple 1");
        shopItemGradeEmpereur.setShopCategory(shopCategoryGrade);
        shopItemGradeEmpereur.setServer(server1);
        shopItemRepository.save(shopItemGradeEmpereur);
        //endregion
    }
}