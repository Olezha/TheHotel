CREATE CACHED TABLE FILE_META
(
  ID        BIGINT         NOT NULL,
  FILENAME  VARCHAR(255),
  UUID      VARCHAR(255)
);

ALTER TABLE FILE_META
  ADD PRIMARY KEY (ID);

INSERT INTO FILE_META (ID,FILENAME,UUID)
VALUES
  (3,'chloe-leis-587461-unsplash.jpg','7c9b9159-e458-4cee-96e1-db3cf1475fb4'),
  (4,'dmitri-popov-69420-unsplash.jpg','459b40cf-5a3f-4eaa-aa22-012885d8dd71'),
  (5,'anna-kaminova-571202-unsplash.jpg','e69a0caf-5e83-4be4-89ab-ee368e17ce5a'),
  (6,'freestocks-org-187367-unsplash.jpg','5e70a460-91e6-46a0-8816-5282e3f96682'),
  (7,'joshua-rawson-harris-602330-unsplash.jpg','03f4b70f-b5ef-4746-81f0-5e61728275ce'),
  (8,'ng-55633-unsplash.jpg','023ffde1-a6a2-465f-8263-0f562dc222af'),
  (9,'nik-lanus-41808-unsplash.jpg','93ecfca8-88a5-4d7c-9132-c504317dbe69'),
  (10,'ialicante-mediterranean-homes-475799-unsplash.jpg','1a71484a-f28b-4eab-944b-0c36867f436f'),
  (11,'feliperizo-co-heart-made-507708-unsplash.jpg','06c9d0cb-abcc-4067-9725-0af4f75c3ca3'),
  (12,'feliperizo-co-heart-made-505565-unsplash.jpg','d0805fb3-9674-4869-a331-ea9d44edea09'),
  (13,'feliperizo-co-heart-made-505561-unsplash.jpg','e54567bc-fe4c-4f6c-9364-1b57d52c6ae5'),
  (14,'christopher-jolly-616571-unsplash.jpg','7e70f143-f05e-4bfd-b973-2bb94119b363'),
  (15,'andrey-yachmenov-28847-unsplash.jpg','09ff2c35-78c2-4aa2-ac20-5260beb54825'),
  (16,'al-ghazali-366777-unsplash.jpg','c8ea96c8-420c-4927-83b6-13137bb46c2e'),
  (17,'alex-block-682877-unsplash.jpg','e44fc049-52d1-4ad8-a277-30c2c1179c7a'),
  (19,'pelle-martin-474958-unsplash.jpg','9e43c206-6377-4295-b056-860f879f8a35'),
  (20,'man-pan-400429-unsplash.jpg','8e387b05-6b70-4166-bd9c-7f3f9ddfb77f'),
  (21,'li-yang-706297-unsplash.jpg','0d0eba3e-df7a-4a1b-a2a1-6d3bf7811732'),
  (22,'kirill-zakharov-630681-unsplash.jpg','6af5f5d3-72c0-4999-8027-6651a410a69a'),
  (23,'justin-docanto-660282-unsplash.jpg','988cca0d-473b-401c-92d6-acbf5eafa504'),
  (24,'ibrahim-boran-575970-unsplash.jpg','fe1d79c5-4908-4e0e-a3cf-ba2dd1ea6d28'),
  (25,'eunice-stahl-572017-unsplash.jpg','f1ba729e-3f46-46fe-9bfc-c8ed3dec9e41'),
  (26,'david-sjunnesson-713325-unsplash.jpg','a55b4903-8236-493c-97ed-b699d2737c18'),
  (27,'deborah-cortelazzi-615800-unsplash.jpg','3f676482-226a-4354-9ab4-99c0b3b04840'),
  (29,'richard-hirajeta-1109603-unsplash.jpg','35fb41f7-8443-4fa6-bfb6-735ed193d0df'),
  (30,'yuris-alhumaydy-671913-unsplash.jpg','6b867dc2-c0c0-42c5-bef3-5c498d6670ce'),
  (31,'rhema-kallianpur-275251-unsplash.jpg','32d0dca4-5817-4acf-a8ba-d6af11c8a706'),
  (32,'rawpixel-1067088-unsplash.jpg','84d0ca07-c4f6-4ca7-b463-0310d2a12f8c'),
  (33,'markus-spiske-37931-unsplash.jpg','5c128168-5dbe-435e-8b56-af9c724c97c5'),
  (34,'jonathan-percy-552030-unsplash.jpg','60db7bf0-a544-4860-bc9a-c3901ecb82a4'),
  (35,'john-cameron-771879-unsplash.jpg','3015cc92-77a8-484d-91dc-554715528992'),
  (36,'jilbert-ebrahimi-434201-unsplash.jpg','30978d3a-7f1b-4985-9c40-e9059ee1c830');

CREATE CACHED TABLE GEO_POINT
(
  ID         BIGINT   NOT NULL,
  LATITUDE   DOUBLE,
  LONGITUDE  DOUBLE
);

ALTER TABLE GEO_POINT
  ADD PRIMARY KEY (ID);

INSERT INTO GEO_POINT (ID,LATITUDE,LONGITUDE)
VALUES
  (1,50.452449,30.46755);

CREATE CACHED TABLE HOTEL
(
  ID            BIGINT         NOT NULL,
  ADDRESS       VARCHAR(255),
  DESCRIPTION   CLOB,
  NAME          VARCHAR(255),
  RULES         CLOB,
  GEO_POINT_ID  BIGINT
);

ALTER TABLE HOTEL
  ADD PRIMARY KEY (ID);

INSERT INTO HOTEL (ID,ADDRESS,DESCRIPTION,NAME,RULES,GEO_POINT_ID)
VALUES
  (2,'Київ','<p>Веб рішення для готелю — інформація, бронювання та календар завантаження готелю <i class="fas fa-thumbs-up"></i></p> <p>Lorem ipsum dolor amet gastropub drinking vinegar glossier wayfarers activated charcoal occupy. IPhone austin fam man bun literally flannel hella disrupt vinyl kale chips cray sartorial cold-pressed subway tile af. Asymmetrical sriracha fashion axe stumptown keffiyeh food truck tumeric organic fam biodiesel waistcoat lumbersexual XOXO. Kombucha offal gluten-free post-ironic, intelligentsia before they sold out meh humblebrag lumbersexual ennui snackwave salvia. Typewriter readymade before they sold out fingerstache intelligentsia viral fanny pack.</p>','Hôtel',NULL,1);

CREATE CACHED TABLE HOTEL_PHOTOS
(
  HOTEL_ID  BIGINT         NOT NULL,
  PHOTOS    VARCHAR(255)
);

INSERT INTO HOTEL_PHOTOS (HOTEL_ID,PHOTOS)
VALUES
  (2,'7c9b9159-e458-4cee-96e1-db3cf1475fb4'),
  (2,'459b40cf-5a3f-4eaa-aa22-012885d8dd71'),
  (2,'e69a0caf-5e83-4be4-89ab-ee368e17ce5a'),
  (2,'5e70a460-91e6-46a0-8816-5282e3f96682'),
  (2,'03f4b70f-b5ef-4746-81f0-5e61728275ce'),
  (2,'023ffde1-a6a2-465f-8263-0f562dc222af');

CREATE CACHED TABLE ROOM
(
  ID           BIGINT          NOT NULL,
  DESCRIPTION  CLOB,
  NUMBER       VARCHAR(255),
  PERSONS      INTEGER,
  PRICE        DECIMAL(19,2),
  HOTEL_ID     BIGINT,
  AWESOME      BOOLEAN
);

ALTER TABLE ROOM
  ADD PRIMARY KEY (ID);

INSERT INTO ROOM (ID,AWESOME,DESCRIPTION,NUMBER,PERSONS,PRICE,HOTEL_ID)
VALUES
  (18,TRUE,'Lorem ipsum dolor amet man bun sriracha before they sold out ennui street art. Thundercats vinyl intelligentsia, tousled brunch vape kickstarter cronut.','115',3,350.00,2),
  (28,TRUE,'Lorem ipsum dolor amet scenester cred stumptown occupy fanny pack health goth seitan poke copper mug typewriter.','116',4,250.00,2),
  (37,FALSE,'Lorem ipsum dolor amet meggings plaid franzen, affogato stumptown gastropub vaporware pork belly schlitz drinking vinegar enamel pin locavore poutine tofu next level. Hexagon drinking vinegar subway tile prism vape chia, affogato paleo salvia banh mi twee cloud bread. Gluten-free retro pickled mustache pour-over polaroid master cleanse YOLO ugh tousled actually pop-up put a bird on it. Cornhole jean shorts four loko vape gluten-free craft beer ennui.','79',2,190.00,2);

CREATE CACHED TABLE ROOM_PHOTOS
(
  ROOM_ID  BIGINT         NOT NULL,
  PHOTOS   VARCHAR(255)
);

INSERT INTO ROOM_PHOTOS (ROOM_ID,PHOTOS)
VALUES
  (18,'93ecfca8-88a5-4d7c-9132-c504317dbe69'),
  (18,'1a71484a-f28b-4eab-944b-0c36867f436f'),
  (18,'06c9d0cb-abcc-4067-9725-0af4f75c3ca3'),
  (18,'d0805fb3-9674-4869-a331-ea9d44edea09'),
  (18,'e54567bc-fe4c-4f6c-9364-1b57d52c6ae5'),
  (18,'7e70f143-f05e-4bfd-b973-2bb94119b363'),
  (18,'09ff2c35-78c2-4aa2-ac20-5260beb54825'),
  (18,'c8ea96c8-420c-4927-83b6-13137bb46c2e'),
  (18,'e44fc049-52d1-4ad8-a277-30c2c1179c7a'),
  (28,'9e43c206-6377-4295-b056-860f879f8a35'),
  (28,'8e387b05-6b70-4166-bd9c-7f3f9ddfb77f'),
  (28,'0d0eba3e-df7a-4a1b-a2a1-6d3bf7811732'),
  (28,'6af5f5d3-72c0-4999-8027-6651a410a69a'),
  (28,'988cca0d-473b-401c-92d6-acbf5eafa504'),
  (28,'f1ba729e-3f46-46fe-9bfc-c8ed3dec9e41'),
  (28,'fe1d79c5-4908-4e0e-a3cf-ba2dd1ea6d28'),
  (28,'a55b4903-8236-493c-97ed-b699d2737c18'),
  (28,'3f676482-226a-4354-9ab4-99c0b3b04840'),
  (37,'35fb41f7-8443-4fa6-bfb6-735ed193d0df'),
  (37,'6b867dc2-c0c0-42c5-bef3-5c498d6670ce'),
  (37,'32d0dca4-5817-4acf-a8ba-d6af11c8a706'),
  (37,'84d0ca07-c4f6-4ca7-b463-0310d2a12f8c'),
  (37,'5c128168-5dbe-435e-8b56-af9c724c97c5'),
  (37,'60db7bf0-a544-4860-bc9a-c3901ecb82a4'),
  (37,'3015cc92-77a8-484d-91dc-554715528992'),
  (37,'30978d3a-7f1b-4985-9c40-e9059ee1c830');

COMMIT;
