/*
Navicat MySQL Data Transfer

Source Server         : Aion Server
Source Server Version : 50557
Source Host           : localhost:3306
Source Database       : al_server_gs

Target Server Type    : MYSQL
Target Server Version : 50557
File Encoding         : 65001

Date: 2025-02-25 10:07:08
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `ingameshop`
-- ----------------------------
DROP TABLE IF EXISTS `ingameshop`;
CREATE TABLE `ingameshop` (
  `object_id` int(11) NOT NULL AUTO_INCREMENT,
  `item_id` int(11) NOT NULL,
  `item_count` bigint(13) NOT NULL DEFAULT '0',
  `item_price` bigint(13) NOT NULL DEFAULT '0',
  `category` tinyint(1) NOT NULL DEFAULT '0',
  `sub_category` tinyint(1) NOT NULL DEFAULT '0',
  `list` int(11) NOT NULL DEFAULT '0',
  `sales_ranking` int(11) NOT NULL DEFAULT '0',
  `item_type` tinyint(1) NOT NULL DEFAULT '0',
  `gift` tinyint(1) NOT NULL DEFAULT '0',
  `title_description` varchar(20) NOT NULL,
  `description` varchar(20) NOT NULL,
  PRIMARY KEY (`object_id`)
) ENGINE=MyISAM AUTO_INCREMENT=6182 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of ingameshop
-- ----------------------------
INSERT INTO `ingameshop` VALUES ('1', '169630003', '1', '200', '0', '10', '1', '66', '0', '0', 'Cube Expansion', 'only buy 2');
INSERT INTO `ingameshop` VALUES ('2', '110900357', '1', '250', '0', '15', '1', '2', '0', '0', 'Pretty Maids Dress', '');
INSERT INTO `ingameshop` VALUES ('3', '169610001', '1', '100', '0', '12', '1', '8', '0', '0', 'Legacy\'s Chosen', ' 7-Day');
INSERT INTO `ingameshop` VALUES ('4', '190100002', '1', '1000', '0', '20', '1', '4', '0', '0', 'Cirruspeed', ' ');
INSERT INTO `ingameshop` VALUES ('5', '190000000', '1', '500', '0', '21', '1', '20', '0', '0', 'Loot, Alert, Eat', 'Shugo');
INSERT INTO `ingameshop` VALUES ('19', '164005001', '1', '250', '0', '22', '1', '7', '0', '0', 'Ring of Fire', ' ');
INSERT INTO `ingameshop` VALUES ('20', '164005005', '1', '250', '0', '22', '1', '24', '0', '0', 'Ring of Magic', ' ');
INSERT INTO `ingameshop` VALUES ('21', '164005004', '1', '250', '0', '22', '1', '6', '0', '0', 'Ring of Earth', ' ');
INSERT INTO `ingameshop` VALUES ('22', '164005003', '1', '250', '0', '22', '1', '8', '0', '0', 'Ring of Wind', ' ');
INSERT INTO `ingameshop` VALUES ('23', '169230001', '1', '50', '0', '18', '1', '1', '0', '0', 'True Red', ' ');
INSERT INTO `ingameshop` VALUES ('24', '169231010', '1', '50', '0', '18', '1', '3', '0', '0', 'Hot Pink', ' ');
INSERT INTO `ingameshop` VALUES ('25', '169231009', '1', '50', '0', '18', '1', '6', '0', '0', 'Hot Orange', ' ');
INSERT INTO `ingameshop` VALUES ('26', '169231008', '1', '50', '0', '18', '1', '11', '0', '0', 'True Black', ' ');
INSERT INTO `ingameshop` VALUES ('27', '169231007', '1', '50', '0', '18', '1', '1', '0', '0', 'Deep Blue', ' ');
INSERT INTO `ingameshop` VALUES ('28', '169230002', '1', '50', '0', '18', '1', '2', '0', '0', 'True White', ' ');
INSERT INTO `ingameshop` VALUES ('29', '169200001', '1', '50', '0', '18', '1', '8', '0', '0', 'Turquoise', ' ');
INSERT INTO `ingameshop` VALUES ('30', '169230004', '1', '50', '0', '18', '1', '0', '0', '0', 'Mustard', ' ');
INSERT INTO `ingameshop` VALUES ('31', '110900083', '1', '250', '0', '15', '1', '1', '0', '0', 'Ribbit Costume', ' ');
INSERT INTO `ingameshop` VALUES ('32', '110900084', '1', '250', '0', '15', '1', '3', '0', '0', 'Wedding Dress/Tuxedo', ' ');
INSERT INTO `ingameshop` VALUES ('33', '110900082', '1', '250', '0', '15', '1', '0', '0', '0', 'Andu Costume Jacket', ' ');
INSERT INTO `ingameshop` VALUES ('6', '164005002', '1', '250', '0', '22', '1', '7', '0', '0', 'Ring of Water', '');
INSERT INTO `ingameshop` VALUES ('319', '110000026', '1', '100', '0', '17', '1', '0', '0', '0', 'Forest Denku\'s Tunic', 'Skin only');
INSERT INTO `ingameshop` VALUES ('7', '169670000', '1', '400', '0', '10', '1', '3', '0', '0', 'Name Change', ' ');
INSERT INTO `ingameshop` VALUES ('9', '164002186', '1', '250', '0', '11', '1', '15', '0', '0', '30 Day Admin Boon', ' ');
INSERT INTO `ingameshop` VALUES ('10', '169640004', '1', '200', '0', '10', '1', '20', '0', '0', 'Warehouse Expand', 'only buy 6');
INSERT INTO `ingameshop` VALUES ('11', '190100023', '1', '1000', '0', '20', '1', '0', '0', '0', 'Sharptooth Barbtail', ' ');
INSERT INTO `ingameshop` VALUES ('12', '190100056', '1', '1000', '0', '20', '1', '18', '0', '0', 'Sharptooth Airspike', ' ');
INSERT INTO `ingameshop` VALUES ('13', '190100046', '1', '1000', '0', '20', '1', '1', '0', '0', 'Hurricane Cirruspeed', ' ');
INSERT INTO `ingameshop` VALUES ('14', '190100032', '1', '1000', '0', '20', '1', '0', '0', '0', 'Pagati Ironhide', ' ');
INSERT INTO `ingameshop` VALUES ('15', '190100051', '1', '1000', '0', '20', '1', '2', '0', '0', 'Flying Pagati', ' ');
INSERT INTO `ingameshop` VALUES ('16', '190100044', '1', '1000', '0', '20', '1', '1', '0', '0', 'Legion Pagati', ' ');
INSERT INTO `ingameshop` VALUES ('17', '169660000', '1', '300', '0', '10', '1', '0', '0', '0', 'Gender Switch', ' ');
INSERT INTO `ingameshop` VALUES ('18', '110900090', '1', '250', '0', '15', '1', '2', '0', '0', 'Spangled Outfit', ' ');
INSERT INTO `ingameshop` VALUES ('110', '190000004', '1', '400', '0', '21', '1', '0', '0', '0', 'Loot, Cooked Items', 'Old Monkey');
INSERT INTO `ingameshop` VALUES ('35', '110900087', '1', '250', '0', '15', '1', '1', '0', '0', 'Cute Denim Dress', ' ');
INSERT INTO `ingameshop` VALUES ('36', '110900088', '1', '250', '0', '15', '1', '2', '0', '0', 'Tell Me Ensemble', ' ');
INSERT INTO `ingameshop` VALUES ('37', '110900089', '1', '250', '0', '15', '1', '1', '0', '0', 'Nobody Retro Dress', ' ');
INSERT INTO `ingameshop` VALUES ('38', '110900050', '1', '250', '0', '15', '1', '5', '0', '0', 'Dynasty Robes', ' ');
INSERT INTO `ingameshop` VALUES ('39', '110900064', '1', '250', '0', '15', '1', '0', '0', '0', 'Daskin\'s Party Suit', ' ');
INSERT INTO `ingameshop` VALUES ('40', '110900078', '1', '250', '0', '15', '1', '0', '0', '0', 'Western Style', ' ');
INSERT INTO `ingameshop` VALUES ('41', '110900050', '1', '250', '0', '15', '1', '1', '0', '0', 'Dynasty Robes', ' ');
INSERT INTO `ingameshop` VALUES ('47', '110900048', '1', '250', '0', '15', '1', '0', '0', '0', 'Dynasty Heavy Armor', '   ');
INSERT INTO `ingameshop` VALUES ('48', '110900172', '1', '250', '0', '15', '1', '1', '0', '0', 'Cotton Sorcerer Robe', ' ');
INSERT INTO `ingameshop` VALUES ('49', '110900178', '1', '250', '0', '15', '1', '1', '0', '0', 'Solorius Tunic', ' ');
INSERT INTO `ingameshop` VALUES ('50', '110900221', '1', '250', '0', '15', '1', '1', '0', '0', 'Cute Playsuit', ' ');
INSERT INTO `ingameshop` VALUES ('51', '110900107', '1', '250', '0', '15', '1', '2', '0', '0', 'Cool Swimsuit', ' ');
INSERT INTO `ingameshop` VALUES ('52', '110900106', '1', '250', '0', '15', '1', '0', '0', '0', 'Cute Swimsuit', ' ');
INSERT INTO `ingameshop` VALUES ('53', '110900112', '1', '100', '0', '15', '1', '0', '0', '0', 'Jester Tunic', ' ');
INSERT INTO `ingameshop` VALUES ('54', '110900113', '1', '250', '0', '15', '1', '0', '0', '0', 'Porgus Costume', ' ');
INSERT INTO `ingameshop` VALUES ('55', '110900155', '1', '250', '0', '15', '1', '0', '0', '0', 'Streaming Happi', ' ');
INSERT INTO `ingameshop` VALUES ('56', '110900260', '1', '250', '0', '15', '1', '0', '0', '0', 'Bard\'s Blouse', ' ');
INSERT INTO `ingameshop` VALUES ('57', '110900264', '1', '250', '0', '15', '1', '0', '0', '0', 'Wonderland Jacket', ' ');
INSERT INTO `ingameshop` VALUES ('58', '110900318', '1', '250', '0', '15', '1', '0', '0', '0', 'Solorius Ensemble', ' ');
INSERT INTO `ingameshop` VALUES ('59', '110900338', '1', '250', '0', '15', '1', '0', '0', '0', 'Seraphim GM Costume', ' ');
INSERT INTO `ingameshop` VALUES ('60', '110900339', '1', '250', '0', '15', '1', '1', '0', '0', 'Shedim GM Costume', ' ');
INSERT INTO `ingameshop` VALUES ('61', '110900340', '1', '250', '0', '15', '1', '0', '0', '0', 'Sweet Couples', ' ');
INSERT INTO `ingameshop` VALUES ('62', '110900341', '1', '250', '0', '15', '1', '1', '0', '0', 'Proud Singles', ' ');
INSERT INTO `ingameshop` VALUES ('63', '110900342', '1', '250', '0', '15', '1', '1', '0', '0', 'Happy Couples', ' ');
INSERT INTO `ingameshop` VALUES ('64', '110900343', '1', '250', '0', '15', '1', '0', '0', '0', 'Trendy Singles', ' ');
INSERT INTO `ingameshop` VALUES ('65', '110900344', '1', '250', '0', '15', '1', '0', '0', '0', 'Snappy Marine Look', ' ');
INSERT INTO `ingameshop` VALUES ('66', '110900348', '1', '250', '0', '15', '1', '0', '0', '0', 'Perky Wedding', ' ');
INSERT INTO `ingameshop` VALUES ('67', '110900350', '1', '250', '0', '15', '1', '0', '0', '0', 'Revel Pumpkin', ' ');
INSERT INTO `ingameshop` VALUES ('68', '110900353', '1', '250', '0', '15', '1', '0', '0', '0', 'Staff Formal', ' ');
INSERT INTO `ingameshop` VALUES ('69', '110900357', '1', '250', '0', '15', '1', '0', '0', '0', 'Tavern Trendwear', ' ');
INSERT INTO `ingameshop` VALUES ('70', '110900368', '1', '250', '0', '15', '1', '0', '0', '0', 'Deepsea Diving Suit', '  ');
INSERT INTO `ingameshop` VALUES ('71', '110900381', '1', '250', '0', '15', '1', '0', '0', '0', 'Cavalry Uniform', ' ');
INSERT INTO `ingameshop` VALUES ('72', '110900384', '1', '250', '0', '15', '1', '0', '0', '0', 'Shadow Wraith Armor', ' ');
INSERT INTO `ingameshop` VALUES ('73', '110900385', '1', '250', '0', '15', '1', '1', '0', '0', 'Shadow Wraith Gi', ' ');
INSERT INTO `ingameshop` VALUES ('74', '110900396', '1', '250', '0', '15', '1', '0', '0', '0', 'Cat Suit', ' ');
INSERT INTO `ingameshop` VALUES ('75', '125045119', '1', '200', '0', '16', '1', '1', '0', '0', 'Raccoon Hat', ' ');
INSERT INTO `ingameshop` VALUES ('76', '110900398', '1', '250', '0', '15', '1', '1', '0', '0', 'Raccoon Suit', ' ');
INSERT INTO `ingameshop` VALUES ('77', '110900397', '1', '250', '0', '15', '1', '0', '0', '0', 'Daru Suit', ' ');
INSERT INTO `ingameshop` VALUES ('78', '110900399', '1', '250', '0', '15', '1', '1', '0', '0', 'Voidtrace Armor Suit', ' ');
INSERT INTO `ingameshop` VALUES ('79', '110900400', '1', '250', '0', '15', '1', '0', '0', '0', 'Majestic Heavy Armor', ' ');
INSERT INTO `ingameshop` VALUES ('80', '110900401', '1', '250', '0', '15', '1', '1', '0', '0', 'Majestic Light Armor', ' ');
INSERT INTO `ingameshop` VALUES ('81', '110900402', '1', '250', '0', '15', '1', '1', '0', '0', 'Majestic Cloth', ' ');
INSERT INTO `ingameshop` VALUES ('82', '110900403', '1', '250', '0', '15', '1', '0', '0', '0', 'Golden Armor', ' ');
INSERT INTO `ingameshop` VALUES ('83', '110900404', '1', '250', '0', '15', '1', '0', '0', '0', 'Cool Cat Outfit', ' ');
INSERT INTO `ingameshop` VALUES ('84', '110900405', '1', '250', '0', '15', '1', '1', '0', '0', 'Code Red', ' ');
INSERT INTO `ingameshop` VALUES ('85', '110900406', '1', '250', '0', '15', '1', '1', '0', '0', 'Code Blue', ' ');
INSERT INTO `ingameshop` VALUES ('86', '110900407', '1', '250', '0', '15', '1', '1', '0', '0', 'Emergency Ensemble', ' ');
INSERT INTO `ingameshop` VALUES ('87', '110900408', '1', '250', '0', '15', '1', '0', '0', '0', 'Spiffy Winter Look', ' ');
INSERT INTO `ingameshop` VALUES ('88', '110900412', '1', '250', '0', '15', '1', '1', '0', '0', 'Tiamat Tee', ' ');
INSERT INTO `ingameshop` VALUES ('89', '110900413', '1', '250', '0', '15', '1', '1', '0', '0', 'Raksha Tee', ' ');
INSERT INTO `ingameshop` VALUES ('90', '110900414', '1', '250', '0', '15', '1', '1', '0', '0', 'Mau Tee', ' ');
INSERT INTO `ingameshop` VALUES ('91', '162001013', '5', '100', '0', '11', '1', '321', '0', '0', 'Tea of Repose', ' ');
INSERT INTO `ingameshop` VALUES ('92', '169620015', '1', '50', '0', '11', '1', '123', '0', '0', '100% XP', 'Lasts 1 hour');
INSERT INTO `ingameshop` VALUES ('93', '169625005', '1', '50', '0', '11', '1', '105', '0', '0', '100% XP & Drop Rate ', 'Lasts 10 min');
INSERT INTO `ingameshop` VALUES ('94', '101501074', '1', '250', '0', '14', '1', '0', '0', '0', 'Dukaki Peon\'s Shovel', 'Skin Only');
INSERT INTO `ingameshop` VALUES ('95', '187050025', '1', '100', '0', '13', '1', '2', '0', '0', 'White Angel Wing', 'Skin Only');
INSERT INTO `ingameshop` VALUES ('96', '187060061', '1', '100', '0', '13', '1', '0', '0', '0', 'Wings of The Circle', 'Asmo Skin Only');
INSERT INTO `ingameshop` VALUES ('97', '187060053', '1', '100', '0', '13', '1', '0', '0', '0', 'Yuditio\'s Wings', 'Elyos Skin Only');
INSERT INTO `ingameshop` VALUES ('98', '187060075', '1', '100', '0', '13', '1', '0', '0', '0', 'Storm Wing Skin', 'Skin Only');
INSERT INTO `ingameshop` VALUES ('99', '187060047', '1', '100', '0', '13', '1', '0', '0', '0', 'Daloshunerks Wing', 'Elyos Skin Only');
INSERT INTO `ingameshop` VALUES ('100', '187060058', '1', '100', '0', '13', '1', '0', '0', '0', 'Abyssal Wing Skin', 'Asmo Skin Only');
INSERT INTO `ingameshop` VALUES ('101', '187060065', '1', '100', '0', '13', '1', '0', '0', '0', 'Agony Wing Skin', 'Asmo Skin Only');
INSERT INTO `ingameshop` VALUES ('102', '187060051', '1', '100', '0', '13', '1', '3', '0', '0', 'Ancanus Wing Skin', 'Elyos Skin Only');
INSERT INTO `ingameshop` VALUES ('103', '187060046', '1', '100', '0', '13', '1', '0', '0', '0', 'Chantinerks Wing', 'Elyos Skin Only');
INSERT INTO `ingameshop` VALUES ('104', '187060076', '1', '100', '0', '13', '1', '0', '0', '0', 'Ancient Spirits', 'Skin Only');
INSERT INTO `ingameshop` VALUES ('105', '187060157', '1', '100', '0', '13', '1', '0', '0', '0', 'Black Angel Wing', 'Skin Only');
INSERT INTO `ingameshop` VALUES ('106', '187060066', '1', '100', '0', '13', '1', '0', '0', '0', 'Chief Crusader\'s', 'Asmo Skin Only');
INSERT INTO `ingameshop` VALUES ('107', '187060055', '1', '100', '0', '13', '1', '1', '0', '0', 'Chief Operative\'s', 'Elyos Skin Only');
INSERT INTO `ingameshop` VALUES ('108', '187060057', '1', '100', '0', '13', '1', '0', '0', '0', 'Despair Wing', 'Asmo Skin Only');
INSERT INTO `ingameshop` VALUES ('109', '187060083', '1', '100', '0', '13', '1', '0', '0', '0', 'Dramata\'s Bone', 'Skin Only');
INSERT INTO `ingameshop` VALUES ('111', '190000005', '1', '450', '0', '21', '1', '0', '0', '0', 'Loot Cook 2 Scroll', 'Baby monkey');
INSERT INTO `ingameshop` VALUES ('112', '190000006', '1', '475', '0', '21', '1', '1', '0', '0', 'Loot Cook 4 Scroll', 'Fat Panda');
INSERT INTO `ingameshop` VALUES ('113', '190000007', '1', '475', '0', '21', '1', '1', '0', '0', 'Loot Drink 6 Scroll', 'Alien Witch');
INSERT INTO `ingameshop` VALUES ('114', '190000009', '1', '400', '0', '21', '1', '1', '0', '0', 'Loot Alert', 'Cute Puppy');
INSERT INTO `ingameshop` VALUES ('115', '190000003', '1', '400', '0', '21', '1', '6', '0', '0', 'Buff Alert 6 Scroll', 'Fat Penguin');
INSERT INTO `ingameshop` VALUES ('116', '169620093', '1', '50', '0', '11', '1', '9', '0', '0', 'Crafting Boost 200%', 'Lasts 2 hours');
INSERT INTO `ingameshop` VALUES ('117', '169620081', '1', '50', '0', '11', '1', '16', '0', '0', 'Gathering Boost 200%', 'Lasts 2 hours');
INSERT INTO `ingameshop` VALUES ('119', '164002184', '1', '150', '0', '11', '1', '11', '0', '0', '7 Day Admin Boon', '7 day pass');
INSERT INTO `ingameshop` VALUES ('120', '169629000', '10', '100', '0', '11', '1', '29', '0', '0', 'Lucky Vinna', 'Lasts 1 hour');
INSERT INTO `ingameshop` VALUES ('121', '160002279', '1', '50', '0', '19', '1', '2', '0', '0', 'Frozen Agrint Candy', 'No craft animation');
INSERT INTO `ingameshop` VALUES ('122', '160002275', '1', '50', '0', '19', '1', '0', '0', '0', 'Pluma Candy', 'No craft animation');
INSERT INTO `ingameshop` VALUES ('123', '160002278', '1', '50', '0', '19', '1', '1', '0', '0', 'Malek Drakie Candy', 'No craft animation');
INSERT INTO `ingameshop` VALUES ('124', '160002398', '1', '50', '0', '19', '1', '8', '0', '0', 'Poco Mookie Candy', ' ');
INSERT INTO `ingameshop` VALUES ('125', '190100096', '1', '1000', '0', '20', '1', '0', '0', '0', 'Event Touring Pagati', ' ');
INSERT INTO `ingameshop` VALUES ('126', '190100163', '1', '1000', '0', '20', '1', '4', '0', '0', 'Koko', ' ');
INSERT INTO `ingameshop` VALUES ('127', '190100105', '1', '1000', '0', '20', '1', '1', '0', '0', 'Frisky Feet', ' ');
INSERT INTO `ingameshop` VALUES ('128', '190100109', '1', '1000', '0', '20', '1', '1', '0', '0', 'Ruddytail Heorn', ' ');
INSERT INTO `ingameshop` VALUES ('129', '190100115', '1', '1000', '0', '20', '1', '1', '0', '0', 'Palomeno Heorn', ' ');
INSERT INTO `ingameshop` VALUES ('130', '190100121', '1', '1000', '0', '20', '1', '0', '0', '0', 'Sharptooth Steam', ' ');
INSERT INTO `ingameshop` VALUES ('131', '190100156', '1', '1000', '0', '20', '1', '3', '0', '0', 'Quick Frillneck', ' ');
INSERT INTO `ingameshop` VALUES ('132', '190100131', '1', '1000', '0', '20', '1', '2', '0', '0', 'Lustrous Skymane', ' ');
INSERT INTO `ingameshop` VALUES ('133', '190100134', '1', '1000', '0', '20', '1', '1', '0', '0', 'Sharptooth Light', ' ');
INSERT INTO `ingameshop` VALUES ('134', '190100135', '1', '1000', '0', '20', '1', '0', '0', '0', 'Sharptooth Audron', ' ');
INSERT INTO `ingameshop` VALUES ('135', '100001364', '1', '250', '0', '14', '1', '0', '0', '0', 'Odella flower', ' ');
INSERT INTO `ingameshop` VALUES ('136', '190100139', '1', '1000', '0', '20', '1', '1', '0', '0', 'Sleek Hovercycle', ' ');
INSERT INTO `ingameshop` VALUES ('137', '190100146', '1', '1000', '0', '20', '1', '3', '0', '0', 'Immortal Knights', ' ');
INSERT INTO `ingameshop` VALUES ('138', '190100150', '1', '1000', '0', '20', '1', '4', '0', '0', 'Shugo Gyrocopter', ' ');
INSERT INTO `ingameshop` VALUES ('139', '187000122', '1', '300', '0', '13', '1', '0', '0', '0', 'Hyperion Wing', 'Skin Only');
INSERT INTO `ingameshop` VALUES ('140', '187050019', '1', '300', '0', '13', '1', '1', '0', '0', 'Popstar Wing', 'Skin Only');
INSERT INTO `ingameshop` VALUES ('141', '187050024', '1', '300', '0', '13', '1', '0', '0', '0', 'Ghostly Angel Wing', 'Skin Only');
INSERT INTO `ingameshop` VALUES ('142', '187060091', '1', '300', '0', '13', '1', '0', '0', '0', 'Shining light Wing', 'Skin Only');
INSERT INTO `ingameshop` VALUES ('143', '187060136', '1', '300', '0', '13', '1', '0', '0', '0', 'Gild Steampunk Wing', 'Skin Only');
INSERT INTO `ingameshop` VALUES ('144', '187060142', '1', '300', '0', '13', '1', '0', '0', '0', 'Prec Steampunk Wing', 'Skin Only');
INSERT INTO `ingameshop` VALUES ('145', '187060148', '1', '300', '0', '13', '1', '1', '0', '0', 'Silv Steampunk Wing', 'Skin Only');
INSERT INTO `ingameshop` VALUES ('146', '187060154', '1', '300', '0', '13', '1', '0', '0', '0', 'Blitzbolt Wing', 'Skin Only');
INSERT INTO `ingameshop` VALUES ('147', '187000140', '1', '300', '0', '13', '1', '1', '0', '0', 'Abyss Officer Wing', 'Skin Only');
INSERT INTO `ingameshop` VALUES ('148', '187060127', '1', '300', '0', '13', '1', '0', '0', '0', 'Red Angel Wing', 'Skin Only');
INSERT INTO `ingameshop` VALUES ('149', '110900441', '1', '250', '0', '15', '1', '0', '0', '0', 'Spotted Snake Cost', 'Skin Only');
INSERT INTO `ingameshop` VALUES ('150', '110900442', '1', '250', '0', '15', '1', '2', '0', '0', 'Rainbow Snake Cost', 'Skin Only');
INSERT INTO `ingameshop` VALUES ('151', '110900443', '1', '250', '0', '15', '1', '0', '0', '0', 'Striped Snake Cost', 'Skin Only');
INSERT INTO `ingameshop` VALUES ('152', '110900529', '1', '250', '0', '15', '1', '0', '0', '0', 'Pirate Crew Attire', 'Skin Only');
INSERT INTO `ingameshop` VALUES ('153', '110900522', '1', '250', '0', '15', '1', '0', '0', '0', 'Pirate Captain Garb', 'Skin Only');
INSERT INTO `ingameshop` VALUES ('154', '125045220', '1', '200', '0', '16', '1', '0', '0', '0', 'Pirate Crew Eyepatch', 'Skin Only');
INSERT INTO `ingameshop` VALUES ('155', '125045195', '1', '200', '0', '16', '1', '0', '0', '0', 'Pirate Capt Topper', 'Skin Only');
INSERT INTO `ingameshop` VALUES ('156', '100001709', '1', '250', '0', '14', '1', '0', '0', '0', 'Danuar Sword', 'Skin Only');
INSERT INTO `ingameshop` VALUES ('157', '100101308', '1', '250', '0', '14', '1', '0', '0', '0', 'Danuar Warhammer', 'Skin Only');
INSERT INTO `ingameshop` VALUES ('158', '100501294', '1', '250', '0', '14', '1', '0', '0', '0', 'Danuar Jewel', 'Skin Only');
INSERT INTO `ingameshop` VALUES ('159', '100201480', '1', '250', '0', '14', '1', '0', '0', '0', 'Danuar Dagger', 'Skin Only');
INSERT INTO `ingameshop` VALUES ('160', '100601406', '1', '250', '0', '14', '1', '2', '0', '0', 'Danuar Tome', 'Skin Only');
INSERT INTO `ingameshop` VALUES ('161', '100901343', '1', '250', '0', '14', '1', '0', '0', '0', 'Danuar Greatsword', 'Skin Only');
INSERT INTO `ingameshop` VALUES ('162', '101301246', '1', '250', '0', '14', '1', '0', '0', '0', 'Danuar Spear', 'Skin Only');
INSERT INTO `ingameshop` VALUES ('163', '101501337', '1', '250', '0', '14', '1', '0', '0', '0', 'Danuar Staff', 'Skin Only');
INSERT INTO `ingameshop` VALUES ('164', '101701345', '1', '250', '0', '14', '1', '0', '0', '0', 'Danuar Bow', 'Skin Only');
INSERT INTO `ingameshop` VALUES ('165', '110101813', '1', '100', '0', '17', '1', '2', '0', '0', 'Danuar Tunic', 'Skin Only');
INSERT INTO `ingameshop` VALUES ('166', '110301794', '1', '100', '0', '17', '1', '0', '0', '0', 'Danuar Jerkin', 'Skin Only');
INSERT INTO `ingameshop` VALUES ('167', '110551130', '1', '100', '0', '17', '1', '1', '0', '0', 'Danuar Hauberk', 'Skin Only');
INSERT INTO `ingameshop` VALUES ('168', '110601597', '1', '100', '0', '17', '1', '0', '0', '0', 'Danuar Breastplate', 'Skin Only');
INSERT INTO `ingameshop` VALUES ('169', '111101631', '1', '100', '0', '17', '1', '2', '0', '0', 'Danuar Gloves', 'Skin Only');
INSERT INTO `ingameshop` VALUES ('170', '111301732', '1', '100', '0', '17', '1', '0', '0', '0', 'Danuar Vambrace', 'Skin Only');
INSERT INTO `ingameshop` VALUES ('171', '111501689', '1', '100', '0', '17', '1', '1', '0', '0', 'Danuar Hanguards', 'Skin Only');
INSERT INTO `ingameshop` VALUES ('172', '111601560', '1', '100', '0', '17', '1', '0', '0', '0', 'Danuar Gauntlets', 'Skin Only');
INSERT INTO `ingameshop` VALUES ('173', '112101581', '1', '100', '0', '17', '1', '2', '0', '0', 'Danuar Pauldrons', 'Skin Only');
INSERT INTO `ingameshop` VALUES ('174', '112301671', '1', '100', '0', '17', '1', '0', '0', '0', 'Danuar Shoulderguard', 'Skin Only');
INSERT INTO `ingameshop` VALUES ('175', '112501628', '1', '100', '0', '17', '1', '1', '0', '0', 'Danuar Spaulders', 'Skin Only');
INSERT INTO `ingameshop` VALUES ('176', '112601542', '1', '100', '0', '17', '1', '0', '0', '0', 'Danuar Shoulderplate', 'Skin Only');
INSERT INTO `ingameshop` VALUES ('177', '113101643', '1', '100', '0', '17', '1', '2', '0', '0', 'Danuar Leggings', 'Skin Only');
INSERT INTO `ingameshop` VALUES ('178', '113301763', '1', '100', '0', '17', '1', '0', '0', '0', 'Danuar Breeches', 'Skin Only');
INSERT INTO `ingameshop` VALUES ('179', '113501707', '1', '100', '0', '17', '1', '1', '0', '0', 'Danuar Chausses', 'Skin Only');
INSERT INTO `ingameshop` VALUES ('180', '113601543', '1', '100', '0', '17', '1', '0', '0', '0', 'Danuar Greaves', 'Skin Only');
INSERT INTO `ingameshop` VALUES ('181', '114101677', '1', '100', '0', '17', '1', '2', '0', '0', 'Danuar Shoes', 'Skin Only');
INSERT INTO `ingameshop` VALUES ('182', '114301800', '1', '100', '0', '17', '1', '0', '0', '0', 'Danuar Boots', 'Skin Only');
INSERT INTO `ingameshop` VALUES ('183', '114501717', '1', '100', '0', '17', '1', '1', '0', '0', 'Danuar Brogans', 'Skin Only');
INSERT INTO `ingameshop` VALUES ('184', '114601550', '1', '100', '0', '17', '1', '0', '0', '0', 'Danuar Sabatons', 'Skin Only');
INSERT INTO `ingameshop` VALUES ('185', '115001734', '1', '250', '0', '14', '1', '0', '0', '0', 'Danuar Shield', 'Skin Only');
INSERT INTO `ingameshop` VALUES ('186', '190100107', '1', '1000', '0', '20', '1', '1', '0', '0', 'Emerald Crestlich', '');
INSERT INTO `ingameshop` VALUES ('187', '125003627', '1', '200', '0', '16', '1', '0', '0', '0', 'Danuar Leather Hat', 'Skin Only');
INSERT INTO `ingameshop` VALUES ('188', '125003625', '1', '200', '0', '16', '1', '1', '0', '0', 'Danuar Cloth Head', 'Skin Only');
INSERT INTO `ingameshop` VALUES ('189', '125003630', '1', '200', '0', '16', '1', '0', '0', '0', 'Danuar Chain Hood', 'Skin Only');
INSERT INTO `ingameshop` VALUES ('190', '125003631', '1', '200', '0', '16', '1', '1', '0', '0', 'Danuar Plate Helm', 'Skin Only');
INSERT INTO `ingameshop` VALUES ('191', '100001471', '1', '250', '0', '14', '1', '1', '0', '0', 'Beritra Sword', 'Skin Only');
INSERT INTO `ingameshop` VALUES ('192', '100101117', '1', '250', '0', '14', '1', '0', '0', '0', 'Beritra Mace', 'Skin Only');
INSERT INTO `ingameshop` VALUES ('193', '100501124', '1', '250', '0', '14', '1', '1', '0', '0', 'Beritra Orb', 'Skin Only');
INSERT INTO `ingameshop` VALUES ('194', '100201280', '1', '250', '0', '14', '1', '1', '0', '0', 'Beritra dagger', 'Skin Only');
INSERT INTO `ingameshop` VALUES ('195', '100601205', '1', '250', '0', '14', '1', '0', '0', '0', 'Beritra Spellbook', 'Skin Only');
INSERT INTO `ingameshop` VALUES ('196', '100901133', '1', '250', '0', '14', '1', '0', '0', '0', 'Beritra Greatsword', 'Skin Only');
INSERT INTO `ingameshop` VALUES ('197', '101301069', '1', '250', '0', '14', '1', '0', '0', '0', 'Beritra Polearm', 'Skin Only');
INSERT INTO `ingameshop` VALUES ('198', '101501150', '1', '250', '0', '14', '1', '0', '0', '0', 'Beritra Staff', 'Skin Only');
INSERT INTO `ingameshop` VALUES ('199', '101701168', '1', '250', '0', '14', '1', '0', '0', '0', 'Beritra Bow', 'Skin Only');
INSERT INTO `ingameshop` VALUES ('200', '110101493', '1', '100', '0', '17', '1', '0', '0', '0', 'Beritra Tunic', 'Skin Only');
INSERT INTO `ingameshop` VALUES ('201', '110301401', '1', '100', '0', '17', '1', '0', '0', '0', 'Beritra Jerkin', 'Skin Only');
INSERT INTO `ingameshop` VALUES ('202', '110501376', '1', '100', '0', '17', '1', '0', '0', '0', 'Beritra Hauberk', 'Skin Only');
INSERT INTO `ingameshop` VALUES ('203', '110601350', '1', '100', '0', '17', '1', '1', '0', '0', 'Beritra Breastplate', 'Skin Only');
INSERT INTO `ingameshop` VALUES ('204', '111101347', '1', '100', '0', '17', '1', '0', '0', '0', 'Beritra Gloves', 'Skin Only');
INSERT INTO `ingameshop` VALUES ('205', '111301342', '1', '100', '0', '17', '1', '0', '0', '0', 'Beritra Vambrace', 'Skin Only');
INSERT INTO `ingameshop` VALUES ('206', '111501334', '1', '100', '0', '17', '1', '0', '0', '0', 'Beritra Handguards', 'Skin Only');
INSERT INTO `ingameshop` VALUES ('207', '111601313', '1', '100', '0', '17', '1', '1', '0', '0', 'Beritra Gauntlets', 'Skin Only');
INSERT INTO `ingameshop` VALUES ('208', '112101304', '1', '100', '0', '17', '1', '0', '0', '0', 'Beritra Pauldrons', 'Skin Only');
INSERT INTO `ingameshop` VALUES ('209', '112301285', '1', '100', '0', '17', '1', '0', '0', '0', 'Beritra Shoulderguar', 'Skin Only');
INSERT INTO `ingameshop` VALUES ('210', '112501274', '1', '100', '0', '17', '1', '0', '0', '0', 'Beritra Spaulders', 'Skin Only');
INSERT INTO `ingameshop` VALUES ('211', '112601293', '1', '100', '0', '17', '1', '1', '0', '0', 'Beritra Shoulderplat', 'Skin Only');
INSERT INTO `ingameshop` VALUES ('212', '113101364', '1', '100', '0', '17', '1', '0', '0', '0', 'Beritra Leggings', 'Skin Only');
INSERT INTO `ingameshop` VALUES ('213', '113301366', '1', '100', '0', '17', '1', '0', '0', '0', 'Beritra Breeches', 'Skin Only');
INSERT INTO `ingameshop` VALUES ('214', '113501349', '1', '100', '0', '17', '1', '0', '0', '0', 'Beritra Chausses', 'Skin Only');
INSERT INTO `ingameshop` VALUES ('215', '113601302', '1', '100', '0', '17', '1', '0', '0', '0', 'Beritra Greaves', 'Skin Only');
INSERT INTO `ingameshop` VALUES ('216', '114101395', '1', '100', '0', '17', '1', '0', '0', '0', 'Beritra Shoes', 'Skin Only');
INSERT INTO `ingameshop` VALUES ('217', '114301401', '1', '100', '0', '17', '1', '0', '0', '0', 'Beritra Boots', 'Skin Only');
INSERT INTO `ingameshop` VALUES ('218', '114501357', '1', '100', '0', '17', '1', '0', '0', '0', 'Beritra Brogans', 'Skin Only');
INSERT INTO `ingameshop` VALUES ('219', '114601299', '1', '100', '0', '17', '1', '1', '0', '0', 'Beritra Sabatons', 'Skin Only');
INSERT INTO `ingameshop` VALUES ('220', '115001492', '1', '250', '0', '14', '1', '0', '0', '0', 'Beritra Shield', 'Skin Only');
INSERT INTO `ingameshop` VALUES ('221', '115001692', '1', '250', '0', '14', '1', '0', '0', '0', 'World Event Shield', 'Skin Only');
INSERT INTO `ingameshop` VALUES ('222', '125003248', '1', '200', '0', '16', '1', '0', '0', '0', 'Beritra Leather Hat', 'Skin Only');
INSERT INTO `ingameshop` VALUES ('223', '125003247', '1', '200', '0', '16', '1', '1', '0', '0', 'Beritra Cloth Head', 'Skin Only');
INSERT INTO `ingameshop` VALUES ('224', '125003249', '1', '200', '0', '16', '1', '0', '0', '0', 'Beritra Chain Hood', 'Skin Only');
INSERT INTO `ingameshop` VALUES ('225', '125003250', '1', '200', '0', '16', '1', '0', '0', '0', 'Beritra Plate Helm', 'Skin Only');
INSERT INTO `ingameshop` VALUES ('226', '100001365', '1', '250', '0', '14', '1', '0', '0', '0', 'Tahabata Sword', 'Skin Only');
INSERT INTO `ingameshop` VALUES ('227', '100101043', '1', '250', '0', '14', '1', '0', '0', '0', 'Tahabata Warhammer', 'Skin Only');
INSERT INTO `ingameshop` VALUES ('228', '100501054', '1', '250', '0', '14', '1', '0', '0', '0', 'Tahabata Orb', 'Skin Only');
INSERT INTO `ingameshop` VALUES ('229', '100201211', '1', '250', '0', '14', '1', '0', '0', '0', 'Tahabata Dagger', 'Skin Only');
INSERT INTO `ingameshop` VALUES ('230', '100601110', '1', '250', '0', '14', '1', '0', '0', '0', 'Tahabata Spellbook', 'Skin Only');
INSERT INTO `ingameshop` VALUES ('231', '100901059', '1', '250', '0', '14', '1', '0', '0', '0', 'Tahabata Greatsword', 'Skin Only');
INSERT INTO `ingameshop` VALUES ('232', '101300997', '1', '250', '0', '14', '1', '0', '0', '0', 'Tahabata Polearm', 'Skin Only');
INSERT INTO `ingameshop` VALUES ('233', '101501075', '1', '250', '0', '14', '1', '1', '0', '0', 'Tahabata Staff', 'Skin Only');
INSERT INTO `ingameshop` VALUES ('234', '101701089', '1', '250', '0', '14', '1', '0', '0', '0', 'Tahabata Bow', 'Skin Only');
INSERT INTO `ingameshop` VALUES ('236', '187060157', '1', '300', '0', '13', '1', '0', '0', '0', 'Black Angel Wings', 'Skin Only');
INSERT INTO `ingameshop` VALUES ('237', '169610002', '1', '200', '0', '12', '1', '1', '0', '0', 'Legacy\'s Chosen', '15-Day');
INSERT INTO `ingameshop` VALUES ('238', '169610003', '1', '350', '0', '12', '1', '11', '0', '0', 'Legacy\'s Chosen', '30-Day');
INSERT INTO `ingameshop` VALUES ('239', '100001367', '1', '250', '0', '14', '1', '0', '0', '0', 'Grogget Sword', 'Skin only');
INSERT INTO `ingameshop` VALUES ('240', '100001368', '1', '250', '0', '14', '1', '0', '0', '0', 'Surama Sword', 'Skin only');
INSERT INTO `ingameshop` VALUES ('241', '100001414', '1', '250', '0', '14', '1', '0', '0', '0', 'Silion Sword', 'Skin only');
INSERT INTO `ingameshop` VALUES ('242', '100101045', '1', '250', '0', '14', '1', '0', '0', '0', 'Grogget warhammer', 'Skin only');
INSERT INTO `ingameshop` VALUES ('243', '100101046', '1', '250', '0', '14', '1', '0', '0', '0', 'Surama Mace', 'Skin only');
INSERT INTO `ingameshop` VALUES ('244', '100101091', '1', '250', '0', '14', '1', '0', '0', '0', 'Silion Mace', 'Skin only');
INSERT INTO `ingameshop` VALUES ('245', '100201213', '1', '250', '0', '14', '1', '0', '0', '0', 'Grogget Dagger', 'Skin only');
INSERT INTO `ingameshop` VALUES ('246', '100201214', '1', '250', '0', '14', '1', '0', '0', '0', 'Surama Dagger', 'Skin only');
INSERT INTO `ingameshop` VALUES ('247', '100201253', '1', '250', '0', '14', '1', '0', '0', '0', 'Silion Dagger', 'Skin only');
INSERT INTO `ingameshop` VALUES ('248', '100201213', '1', '250', '0', '14', '1', '0', '0', '0', 'Grogget Dagger', 'Skin only');
INSERT INTO `ingameshop` VALUES ('249', '100201214', '1', '250', '0', '14', '1', '0', '0', '0', 'Surama Dagger', 'Skin only');
INSERT INTO `ingameshop` VALUES ('250', '100201253', '1', '250', '0', '14', '1', '0', '0', '0', 'Silion Dagger', 'Skin only');
INSERT INTO `ingameshop` VALUES ('251', '100501056', '1', '250', '0', '14', '1', '0', '0', '0', 'Grogget Jewel', 'Skin only');
INSERT INTO `ingameshop` VALUES ('252', '100501057', '1', '250', '0', '14', '1', '0', '0', '0', 'Surama Orb', 'Skin only');
INSERT INTO `ingameshop` VALUES ('253', '100501099', '1', '250', '0', '14', '1', '0', '0', '0', 'Silion Orb', 'Skin only');
INSERT INTO `ingameshop` VALUES ('254', '110900361', '1', '250', '0', '15', '1', '0', '0', '0', 'Striped cost', 'Skin only');
INSERT INTO `ingameshop` VALUES ('255', '110900362', '1', '250', '0', '15', '1', '0', '0', '0', 'Kimono', 'Skin only');
INSERT INTO `ingameshop` VALUES ('256', '110900364', '1', '250', '0', '15', '1', '0', '0', '0', 'Chipao/Chanpao', 'Skin only');
INSERT INTO `ingameshop` VALUES ('257', '100601112', '1', '250', '0', '14', '1', '0', '0', '0', 'Grogget Tome', 'Skin only');
INSERT INTO `ingameshop` VALUES ('258', '100601113', '1', '250', '0', '14', '1', '0', '0', '0', 'Surama Spellbook', 'Skin only');
INSERT INTO `ingameshop` VALUES ('259', '100601155', '1', '250', '0', '14', '1', '0', '0', '0', 'Silion Spellbook', 'Skin only');
INSERT INTO `ingameshop` VALUES ('260', '101300999', '1', '250', '0', '14', '1', '0', '0', '0', 'Grogget Spear', 'Skin only');
INSERT INTO `ingameshop` VALUES ('261', '101301000', '1', '250', '0', '14', '1', '0', '0', '0', 'Surama Polearm', 'Skin only');
INSERT INTO `ingameshop` VALUES ('262', '101301044', '1', '250', '0', '14', '1', '0', '0', '0', 'Silion Polearm', 'Skin only');
INSERT INTO `ingameshop` VALUES ('263', '100901061', '1', '250', '0', '14', '1', '0', '0', '0', 'Grogget Greatsword', 'Skin only');
INSERT INTO `ingameshop` VALUES ('264', '100901062', '1', '250', '0', '14', '1', '0', '0', '0', 'Surama Greatsword', 'Skin only');
INSERT INTO `ingameshop` VALUES ('265', '100901107', '1', '250', '0', '14', '1', '0', '0', '0', 'Silion Greatsword', 'Skin only');
INSERT INTO `ingameshop` VALUES ('266', '100901061', '1', '250', '0', '14', '1', '0', '0', '0', 'Grogget Greatsword', 'Skin only');
INSERT INTO `ingameshop` VALUES ('267', '100901062', '1', '250', '0', '14', '1', '0', '0', '0', 'Surama Greatsword', 'Skin only');
INSERT INTO `ingameshop` VALUES ('268', '100901107', '1', '250', '0', '14', '1', '0', '0', '0', 'Silion Greatsword', 'Skin only');
INSERT INTO `ingameshop` VALUES ('269', '101501077', '1', '250', '0', '14', '1', '0', '0', '0', 'Grogget Staff', 'Skin only');
INSERT INTO `ingameshop` VALUES ('270', '101501078', '1', '250', '0', '14', '1', '0', '0', '0', 'Surama Staff', 'Skin only');
INSERT INTO `ingameshop` VALUES ('271', '101501125', '1', '250', '0', '14', '1', '0', '0', '0', 'Silion Staff', 'Skin only');
INSERT INTO `ingameshop` VALUES ('272', '101701091', '1', '250', '0', '14', '1', '0', '0', '0', 'Grogget Longbow', 'Skin only');
INSERT INTO `ingameshop` VALUES ('273', '101701092', '1', '250', '0', '14', '1', '0', '0', '0', 'Surama Bow', 'Skin only');
INSERT INTO `ingameshop` VALUES ('274', '101701136', '1', '250', '0', '14', '1', '0', '0', '0', 'Silion Bow', 'Skin only');
INSERT INTO `ingameshop` VALUES ('275', '101701137', '1', '250', '0', '14', '1', '0', '0', '0', 'Siel Dom Bow', 'Skin only');
INSERT INTO `ingameshop` VALUES ('276', '101301045', '1', '250', '0', '14', '1', '0', '0', '0', 'Siel Dom PA', 'Skin only');
INSERT INTO `ingameshop` VALUES ('277', '101501126', '1', '250', '0', '14', '1', '0', '0', '0', 'Siel Dom Staff', 'Skin only');
INSERT INTO `ingameshop` VALUES ('278', '100901108', '1', '250', '0', '14', '1', '0', '0', '0', 'Siel Dom GS', 'Skin only');
INSERT INTO `ingameshop` VALUES ('279', '100901108', '1', '250', '0', '14', '1', '0', '0', '0', 'Siel Dom Tome', 'Skin only');
INSERT INTO `ingameshop` VALUES ('280', '100501100', '1', '250', '0', '14', '1', '0', '0', '0', 'Siel Dom Orb', 'Skin only');
INSERT INTO `ingameshop` VALUES ('281', '100201254', '1', '250', '0', '14', '1', '0', '0', '0', 'Siel Dom Dag', 'Skin only');
INSERT INTO `ingameshop` VALUES ('282', '100101092', '1', '250', '0', '14', '1', '0', '0', '0', 'Siel Dom Mace', 'Skin only');
INSERT INTO `ingameshop` VALUES ('283', '100001415', '1', '250', '0', '14', '1', '0', '0', '0', 'Siel Dom Sword', 'Skin only');
INSERT INTO `ingameshop` VALUES ('284', '110900386', '1', '100', '0', '17', '1', '0', '0', '0', 'Dbound Breastpl', 'Skin Only');
INSERT INTO `ingameshop` VALUES ('285', '110900387', '1', '100', '0', '17', '1', '0', '0', '0', 'Dbound Hauberk', 'Skin Only');
INSERT INTO `ingameshop` VALUES ('286', '110900388', '1', '100', '0', '17', '1', '0', '0', '0', 'Dbound Jerkin', 'Skin Only');
INSERT INTO `ingameshop` VALUES ('287', '110900389', '1', '100', '0', '17', '1', '0', '0', '0', 'Dbound Tunic', 'Skin Only');
INSERT INTO `ingameshop` VALUES ('288', '110900390', '1', '100', '0', '17', '1', '0', '0', '0', 'Veille Jerkin', 'Skin Only');
INSERT INTO `ingameshop` VALUES ('289', '110900389', '1', '100', '0', '17', '1', '0', '0', '0', 'Mastarius Jerkin', 'Skin Only');
INSERT INTO `ingameshop` VALUES ('290', '110900399', '1', '250', '0', '15', '1', '0', '0', '0', 'Firebolt armor', 'Skin Only');
INSERT INTO `ingameshop` VALUES ('291', '111000012', '1', '100', '0', '17', '1', '1', '0', '0', 'Jester Gloves', 'Skin Only');
INSERT INTO `ingameshop` VALUES ('292', '114000012', '1', '100', '0', '17', '1', '1', '0', '0', 'Jester Shoes', 'Skin Only');
INSERT INTO `ingameshop` VALUES ('293', '125040059', '1', '200', '0', '16', '1', '0', '0', '0', 'Jester Hat', 'Skin Only');
INSERT INTO `ingameshop` VALUES ('294', '110900009', '1', '250', '0', '15', '1', '1', '0', '0', 'Kor striped ', ' Female only');
INSERT INTO `ingameshop` VALUES ('295', '110900008', '1', '250', '0', '15', '1', '1', '0', '0', 'Kor vest', ' Female only');
INSERT INTO `ingameshop` VALUES ('296', '110900006', '1', '250', '0', '15', '1', '1', '0', '0', 'Kor striped', ' Male only');
INSERT INTO `ingameshop` VALUES ('297', '110900005', '1', '250', '0', '15', '1', '1', '0', '0', 'Kor vest', ' Male only');
INSERT INTO `ingameshop` VALUES ('298', '110900215', '1', '250', '0', '15', '1', '2', '0', '0', 'Couture Clothing', ' Skin only');
INSERT INTO `ingameshop` VALUES ('299', '110900217', '1', '250', '0', '15', '1', '1', '0', '0', 'I can hear only', ' Skin only');
INSERT INTO `ingameshop` VALUES ('300', '110900218', '1', '250', '0', '15', '1', '1', '0', '0', 'I am yours', ' Skin only');
INSERT INTO `ingameshop` VALUES ('301', '110900221', '1', '250', '0', '15', '1', '1', '0', '0', 'Cute and sprightly', ' Skin only');
INSERT INTO `ingameshop` VALUES ('302', '110900223', '1', '250', '0', '15', '1', '1', '0', '0', 'Gym suit', ' Skin only');
INSERT INTO `ingameshop` VALUES ('303', '110900230', '1', '250', '0', '15', '1', '1', '0', '0', 'Neat marine', ' Skin only');
INSERT INTO `ingameshop` VALUES ('304', '110900232', '1', '250', '0', '15', '1', '1', '0', '0', 'Koinobori festival', ' Skin only');
INSERT INTO `ingameshop` VALUES ('305', '110900242', '1', '250', '0', '15', '1', '1', '0', '0', 'My other half', ' Skin only');
INSERT INTO `ingameshop` VALUES ('306', '110900263', '1', '250', '0', '15', '1', '1', '0', '0', 'Bards Blouse', ' Skin only');
INSERT INTO `ingameshop` VALUES ('307', '110900267', '1', '250', '0', '15', '1', '1', '0', '0', 'Wonderland Waist', ' Skin only');
INSERT INTO `ingameshop` VALUES ('308', '110900302', '1', '250', '0', '15', '1', '1', '0', '0', 'Graceful trad', ' Skin only');
INSERT INTO `ingameshop` VALUES ('309', '110900307', '1', '250', '0', '15', '1', '1', '0', '0', 'Luxurious trad', ' Skin only');
INSERT INTO `ingameshop` VALUES ('310', '110900312', '1', '250', '0', '15', '1', '1', '0', '0', 'Opulent trad', ' Skin only');
INSERT INTO `ingameshop` VALUES ('311', '110900329', '1', '250', '0', '15', '1', '2', '0', '0', 'Red stume of luck', ' Skin only');
INSERT INTO `ingameshop` VALUES ('312', '110900338', '1', '250', '0', '15', '1', '1', '0', '0', 'Seraphim GM', ' Skin only');
INSERT INTO `ingameshop` VALUES ('313', '110900339', '1', '250', '0', '15', '1', '1', '0', '0', 'Shedim GM', ' Skin only');
INSERT INTO `ingameshop` VALUES ('314', '110900340', '1', '250', '0', '15', '1', '1', '0', '0', 'Sweet couple', ' Skin only');
INSERT INTO `ingameshop` VALUES ('315', '110900341', '1', '250', '0', '15', '1', '2', '0', '0', 'Proud single', ' Skin only');
INSERT INTO `ingameshop` VALUES ('316', '110900353', '1', '250', '0', '15', '1', '1', '0', '0', 'Brax cafe', ' Skin only');
INSERT INTO `ingameshop` VALUES ('317', '110900357', '1', '250', '0', '15', '1', '1', '0', '0', 'Cute brax cafe', 'Skin only');
INSERT INTO `ingameshop` VALUES ('318', '186000252', '1', '1000', '0', '23', '1', '4', '0', '0', 'Legacy VIP', '30 Day');
INSERT INTO `ingameshop` VALUES ('320', '169650000', '1', '300', '0', '10', '1', '3', '0', '0', 'Plastic Surgery', ' ');
INSERT INTO `ingameshop` VALUES ('321', '111000079', '1', '100', '0', '17', '1', '1', '0', '0', 'Forest Denku\'s Hand', 'Skin only');
INSERT INTO `ingameshop` VALUES ('322', '112000038', '1', '100', '0', '17', '1', '1', '0', '0', 'Forest Denku\'s Shoul', 'Skin only');
INSERT INTO `ingameshop` VALUES ('323', '113000049', '1', '100', '0', '17', '1', '1', '0', '0', 'Forest Denku\'s Leg', 'Skin only');
INSERT INTO `ingameshop` VALUES ('324', '114000087', '1', '100', '0', '17', '1', '1', '0', '0', 'Forest Denku\'s Foot', 'Skin only');
INSERT INTO `ingameshop` VALUES ('325', '125045558', '1', '200', '0', '16', '1', '0', '0', '0', 'Forest Denku\'s Head', 'Skin only');
INSERT INTO `ingameshop` VALUES ('326', '110000026', '1', '100', '0', '17', '1', '0', '0', '0', 'Forest Denku\'s Tunic', 'Skin only');
