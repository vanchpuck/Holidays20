delete from t_monthfloatholidays where id_holiday = 498;
delete from t_countryholidays where id_holiday = 498;
delete from t_holidays where _id = 498;

update t_holidays set description='День Эколога был утвержден во «Всемирный день окружающей среды», ежегодно отмечаемый 5 июня. Таким образом, главный праздник экологов совпал с главной международной экологической датой.' 
where _id = 106;

update t_holidays set description='Был учрежден указом президиума Верховного Совета СССР от 29 сентября 1965 года. В Белоруссии, Казахстане, Киргизии, Латвии, Молдавии, Украине отмечается в первое воскресенье октября.' 
where _id = 334;

update t_holidays set description='День машиностроителя отмечается в России на основании Указа Президиума Верховного Совета СССР от 1 октября 1980 года «О праздничных и памятных днях». Этот профессиональный праздник отмечают рабочие и инженеры машиностроительной отрасли.' 
where _id = 138;

update t_holidays set title='день местного самоуправления'
where _id = 195;

update t_holidays set title='день науки'
where _id = 159;

update t_holidays set title='день работников местной промышленности'
where _id = 150;

update t_holidays set title='день памяти жертв голодомора и политических репрессий'
where _id = 294;

update t_holidays set actualDateStr='18 апреля', day=18
where _id = 300;

insert into t_holidays(_id, title, description, month, day, actualDateStr, id_priority, id_image) 
values (
100000,
'день работника торговли', 
'Дата праздования установлена указом президента от 7 мая 2013 года. Ранее работники торговли отмечали свой профессиональный праздник в третье воскресенье марта.',
6, null, 'Четвертая суббота июля', 2, 85);
insert into t_countryholidays(id_country, id_holiday) values (2, 100000);
insert into t_monthfloatholidays(id_holiday, month, weekday, dayoffset) values(100000, 6, 6, 21);

insert into t_holidays(_id, title, description, month, day, actualDateStr, id_priority, id_image) 
values (
100001,
'день согласия и примирения', 
'День согласия и примирения — официальное название праздника, отмечавшегося ранее в России 7 ноября. В СССР дни 7 и 8 ноября праздновались как годовщина Октябрьской революции. Празднование 7 ноября как одного из важнейших государственных праздников сохранялось в России до 2004. В 1996 году прежнее название праздника — «Годовщина Великой Октябрьской социалистической революции» — было изменено на современное. Начиная с 2005 года, день 7 ноября перестал быть выходным днём. Вместо него выходным днём стал День народного единства, который отмечают 4 ноября.',
10, 7, '7 ноября', 5, 54);
insert into t_countryholidays(id_country, id_holiday) values (2, 100001);

insert into t_holidays(_id, title, description, month, day, actualDateStr, id_priority, id_image) 
values (
100002,
'день войск радиационной, химической и биологической защиты', 
'В Рабоче-крестьянской Красной армии химические войска стали складываться в конце 1918 года. 13 ноября 1918 г., приказом Реввоенсовета Республики № 220 была создана Химическая служба РККА. В августе 1992 года химические войска получили свое современное название.',
10, 13, '13 ноября', 2, 159);
insert into t_countryholidays(id_country, id_holiday) values (2, 100002);

insert into t_holidays(_id, title, description, month, day, actualDateStr, id_priority, id_image) 
values (
100003,
'день октябрьской революции', 
'Государственный праздник в СССР. Отмечался в день свершения Октябрьской революции ежегодно 7 и 8 ноября. Праздновался с 1917 года. В этот день на Красной площади в Москве, а также в областных и краевых центрах СССР проходили демонстрации трудящихся и военные парады. Последний военный парад на Красной площади Москвы в ознаменование годовщины Октябрьской революции прошёл в 1990 году. До сих пор День Октябрьской революции празднуется в Белоруссии, Киргизии и Приднестровье.',
10, 7, '7 ноября', 5, 43);
insert into t_countryholidays(id_country, id_holiday) values (3, 100003);

insert into t_holidays(_id, title, description, month, day, actualDateStr, id_priority, id_image) 
values (
100004,
'день памяти', 
'В Беларуси данный праздник считается государственным Днем памяти. В этот день принято устраивать домашний семейный обед или ужин, на который, как верят белорусы, к ним в гости придут предки.',
10, 2, '2 ноября', 4, 50);
insert into t_countryholidays(id_country, id_holiday) values (3, 100004);

insert into t_holidays(_id, title, description, month, day, actualDateStr, id_priority, id_image) 
values (
100005,
'день работников физической культуры и спорта', 
'Первая спортивная организация в Беларуси, Минское общество любителей спорта, была создана в 1892 г.  В 1952 г. белорусские спортсмены в составе сборной команды СССР впервые участвовали в летних Олимпийских играх в Хельсинки. Как независимое государство Беларусь участвовала в зимних Олимпийских играх в Лиллехаммере в 1994 году и завоевала две серебряные медали.',
4, null, 'Третья суббота мая', 2, 98);
insert into t_countryholidays(id_country, id_holiday) values (3, 100005);
insert into t_monthfloatholidays(id_holiday, month, weekday, dayoffset) values(100005, 4, 6, 14);

insert into t_holidays(_id, title, description, month, day, actualDateStr, id_priority, id_image) 
values (
100006,
'день кооперации', 
'Кооперативное движение существует в Беларуси более 100 лет и является важным элементом экономики страны, обеспечения материальных и иных потребностей населения. В состав потребительской кооперации республики входят 119 потребительских обществ, 6 областных союзов и республиканский союз потребительских обществ. ',
6, null, 'Первая суббота июля', 2, 50);
insert into t_countryholidays(id_country, id_holiday) values (3, 100006);
insert into t_monthfloatholidays(id_holiday, month, weekday, dayoffset) values(100006, 6, 6, 0);

insert into t_holidays(_id, title, description, month, day, actualDateStr, id_priority, id_image) 
values (
100007,
'день страховых работников', 
'3 декабря 1921 года, был принят Декрет Совнаркома БССР "Об организации государственного имущественного страхования в БССР". В соответствии с ним в составе Народного комиссариата финансов было создано Управление государственного страхования, ныне - Белорусская государственная страховая организация, или Белгосстрах.',
11, null, 'Первая суббота декабря', 2, 32);
insert into t_countryholidays(id_country, id_holiday) values (3, 100007);
insert into t_monthfloatholidays(id_holiday, month, weekday, dayoffset) values(100007, 11, 6, 0);

insert into t_holidays(_id, title, description, month, day, actualDateStr, id_priority, id_image) 
values (
100008,
'день энергетика', 
'В 1920 году состоялся 8-ой Всероссийский съезд Советов, на котором был принят Государственный план электрификации России (ГОЭЛРО).
В память об этом событии Президиум Верховного Совета СССР провозгласил двадцать второй декабрьский день Днем энергетика.
Однако следуя новому Указу ПВС СССР от 1 ноября 1988 года, праздник стали отмечать в третье воскресенье декабря. Но эта традиция не прижилась, и День энергетика до сих пор празднуют 22 декабря.',
11, 22, '22 декабря', 2, 103);
insert into t_countryholidays(id_country, id_holiday) values (3, 100008);
insert into t_countryholidays(id_country, id_holiday) values (4, 100008);

insert into t_holidays(_id, title, description, month, day, actualDateStr, id_priority, id_image) 
values (
100009,
'всемирный день ди-джея', 
'Праздник был учрежден организацией ЮНЕСКО в благотворительных целях. Организаторы фонда World DJ Day Foundation призывают диджеев пожертвовать сегодня все заработанные в эту ночь деньги детским домам и благотворительным организациям.',
2, 9, '9 марта', 1, 147);
insert into t_countryholidays(id_country, id_holiday) values (1, 100009);

insert into t_holidays(_id, title, description, month, day, actualDateStr, id_priority, id_image) 
values (
100010,
'день работника налоговой и таможенной службы украины', 
'Согласно указу президента Украины от 11 октября 2013 года, датой празднования дня работника налоговой и таможенной службы Украины стало 18 марта. Таким образом, существовавшие ранее "День таможенной службы" и "День работников налоговой службы" были объединены в одну праздничную дату. 
',
2, 18, '18 марта', 2, 48);
insert into t_countryholidays(id_country, id_holiday) values (4, 100010);

insert into t_holidays(_id, title, description, month, day, actualDateStr, id_priority, id_image) 
values (
100011,
'международный день театра кукол', 
'Идея празднования во всем мире Международного дня кукольника  пришла известному деятелю кукольного театра Дживада Золфагарихо из Ирана. Первое празднование прошло 21 марта 2003 года.',
2, 21, '21 марта', 2, 72);
insert into t_countryholidays(id_country, id_holiday) values (1, 100011);

insert into t_holidays(_id, title, description, month, day, actualDateStr, id_priority, id_image) 
values (
100012,
'всемирный день поэзии', 
'Праздник был учрежден ЮНЕСКО  15 ноября 1999 года. Как отмечалось в решении ЮНЕСКО, цель учреждения праздника — «придать новый импульс и новое признание национальным, региональным и международным поэтическим движениям».',
2, 21, '21 марта', 1, 26);
insert into t_countryholidays(id_country, id_holiday) values (1, 100012);

insert into t_holidays(_id, title, description, month, day, actualDateStr, id_priority, id_image) 
values (
100013,
'международный день таксиста', 
'В этот день в 1907 году на улицах Лондона появились первые автомобили, оснащенные специальными счетчиками - "таксометрами". Именно тогда на машины (кэбы) были установлены таксометры. С тех пор индивидуальный городской транспорт стали называть такси, а извозчиков — таксистами.',
2, 22, '22 марта', 1, 22);
insert into t_countryholidays(id_country, id_holiday) values (1, 100013);

insert into t_holidays(_id, title, description, month, day, actualDateStr, id_priority, id_image) 
values (
100014,
'день веб-мастера', 
'Ежегодно 4 апреля отмечается неофициальный профессиональный праздник — День веб-мастера. Дата празднования выбрана не случайно, цифры 4.04 напоминают по своему написанию ошибку 404 («Страница не найдена»).',
3, 4, '4 апреля', 1, 56);
insert into t_countryholidays(id_country, id_holiday) values (1, 100014);

insert into t_holidays(_id, title, description, month, day, actualDateStr, id_priority, id_image) 
values (
100015,
'международный день танца', 
'Инициирован в 1982 году Международным советом танца ЮНЕСКО. Дата была предложена артистом балета, педагогом и хореографом П. А. Гусевым в память о родившемся в этот день французском балетмейстере, теоретике и реформаторе балета Ж.-Ж. Новерре, вошедшем в историю как «отец современного балета».',
3, 29, '29 апреля', 1, 100);
insert into t_countryholidays(id_country, id_holiday) values (1, 100015);

insert into t_holidays(_id, title, description, month, day, actualDateStr, id_priority, id_image) 
values (
100016,
'день участкового инспектора милиции', 
'Днем рождения службы УИМ на Украине принято считать 18 июня 1923 года — день, когда народный комиссар внутренних дел милиции УССР подписал приказ «Об утверждении инструкции для районных наблюдателей городских отделов». Официально праздник был учрежден 18 июня 2004 года.',
5, 18, '18 июня', 2, 18);
insert into t_countryholidays(id_country, id_holiday) values (4, 100016);

insert into t_holidays(_id, title, description, month, day, actualDateStr, id_priority, id_image) 
values (
100017,
'всемирный день рыболовства', 
'Праздник установлен решением Международной конференции по регулированию и развитию рыболовства, состоявшейся в июле 1984 года в Риме.',
5, 27, '27 июня', 1, 31);
insert into t_countryholidays(id_country, id_holiday) values (1, 100017);

insert into t_holidays(_id, title, description, month, day, actualDateStr, id_priority, id_image) 
values (
100018,
'день следователя', 
'В этот день свой профессиональный праздник отмечают все следователи Украины. Следователь в уголовно-процессуальном праве — должностное лицо, уполномоченное осуществлять предварительное следствие по уголовному делу, а также иные полномочия, предусмотренные процессуальным законодательством.',
6, 1, '1 июля', 2, 25);
insert into t_countryholidays(id_country, id_holiday) values (4, 100018);

insert into t_holidays(_id, title, description, month, day, actualDateStr, id_priority, id_image) 
values (
100019,
'день фотографа', 
'День фотографа отмечается 12 июля - это день святой Вероники, которая считается покровительницей фотографии. Святая Вероника согласно легенде, подала Иисусу, идущему на Голгофу, ткань, чтобы он смог стереть пот с лица. На этой ткани запечатлелся лик Христа. Почти два тысячелетия спустя, когда изобрели фотографию, Римский папа объявил этот день всемирным днем фотографа.',
6, 12, '12 июля', 2, 150);
insert into t_countryholidays(id_country, id_holiday) values (1, 100019);

insert into t_holidays(_id, title, description, month, day, actualDateStr, id_priority, id_image) 
values (
100020,
'день дальнобойщика', 
'День дальнобойщика - это профессиональный праздник тысяч водителей, королей дорог. На Украине Дню дальнобойщика посвящен целый фестиваль под одноименным названием «День дальнобойщика». В рамках этого фестиваля организаторы устраивают пресс-конференции, на которых озвучиваются актуальные проблемы, волнующие сегодняшних дальнобойщиков и водителей.',
7, null, 'Последняя суббота августа', 2, 22);
insert into t_countryholidays(id_country, id_holiday) values (4, 100020);
insert into t_monthfloatholidays(id_holiday, month, weekday, dayoffset) values(100020, 8, 6, -7);

insert into t_holidays(_id, title, description, month, day, actualDateStr, id_priority, id_image) 
values (
100021,
'день военной разведки', 
'День военной разведки был учрежден в 2007 году. В этот день в 1992 году был создан разведывательный орган стратегического уровня в составе оборонного ведомства, что стало началом истории военной разведки независимой Украины.',
8, 7, '7 сентября', 2, 48);
insert into t_countryholidays(id_country, id_holiday) values (4, 100021);

insert into t_holidays(_id, title, description, month, day, actualDateStr, id_priority, id_image) 
values (
100022,
'день ветерана', 
'Праздник отмечается на территории Украниы с 2004 года. Дата празднования совпадает с международным днем лиц преклонных лет, учрежденным ООН.',
9, 1, '1 октября', 5, 95);
insert into t_countryholidays(id_country, id_holiday) values (4, 100022);

insert into t_holidays(_id, title, description, month, day, actualDateStr, id_priority, id_image) 
values (
100023,
'день риэлтора', 
'День риэлтора пока не закреплён в официальном календаре праздников Украины. Начиная с 2000 года ежегодно отмечается 9 октября.',
9, 9, '9 октября', 2, 137);
insert into t_countryholidays(id_country, id_holiday) values (4, 100023);

insert into t_holidays(_id, title, description, month, day, actualDateStr, id_priority, id_image) 
values (
100024,
'всемирный день анестезии', 
'История анестезии началась 16 октября 1846 года. Зубной врач Томас Мортон впервые провел операцию под эфирным наркозом. Это день во всем мире принято считать Всемирным днем анестезии.',
9, 16, '16 октября', 2, 41);
insert into t_countryholidays(id_country, id_holiday) values (1, 100024);

insert into t_holidays(_id, title, description, month, day, actualDateStr, id_priority, id_image) 
values (
100025,
'день работников суда', 
'15 декабря Указом Президента от 08.12.2000 № 1318/2000 в Украине установлен ежегодный профессиональный праздник — День работников суда. В систему судебных органов Украины входят конституционный Суд Украины, суды общей юрисдикции и арбитражные суды.',
11, 15, '15 декабря', 2, 23);
insert into t_countryholidays(id_country, id_holiday) values (1, 100025);

insert into t_holidays(_id, title, description, month, day, actualDateStr, id_priority, id_image) 
values (
100026,
'всемирный день справедливой торговли', 
'Всемирный день справедливой торговли. Отмечается во вторую субботу мая, начиная с середины 60-х годов ХХ века.',
4, null, 'Вторая суббота мая', 1, 32);
insert into t_countryholidays(id_country, id_holiday) values (1, 100026);
insert into t_monthfloatholidays(id_holiday, month, weekday, dayoffset) values(100026, 4, 6, 7);

insert into t_holidays(_id, title, description, month, day, actualDateStr, id_priority, id_image) 
values (
100027,
'день полярника', 
'День полярника — профессиональный праздник, установленный указом Президента Российской Федерации в 2013 году в знак признания заслуг полярников. Отмечается 21 мая. Выбор даты связан с датой открытия 21 мая 1937 года первой дрейфующей полярной станции «Северный полюс-1».',
4, 21, '21 мая', 2, 52);
insert into t_countryholidays(id_country, id_holiday) values (2, 100027);

insert into t_holidays(_id, title, description, month, day, actualDateStr, id_priority, id_image) 
values (
100028,
'день сотрудника органов следствия российской федерации', 
'Профессиональный праздник сотрудников и работников Следственного комитета Российской Федерации, следственных подразделений Министерства внутренних дел, Федеральной службы безопасности, Федеральной службы по контролю за оборотом наркотиков Российской Федерации. Выбор указанной даты обусловлен тем, что в этот день 25 июля 1713 года был издан именной указ Петра I «О создании следственной канцелярии гвардии майора М.И. Волконского», ставшей первым государственным органом России, подчинённым непосредственно главе государства и наделённым полномочиями по проведению предварительного следствия.',
6, 25, '25 июля', 2, 23);
insert into t_countryholidays(id_country, id_holiday) values (2, 100028);

insert into t_holidays(_id, title, description, month, day, actualDateStr, id_priority, id_image) 
values (
100029,
'день работников дорожного хозяйства', 
'В соответствии с Указом Президента РФ от 23 марта 2000 г. N 556 «О Дне работников дорожного хозяйства» профессиональный праздник работников дорожного хозяйства отмечается каждый год в третье воскресенье октября.',
9, null, 'Третье воскресенье октября', 2, 92);
insert into t_countryholidays(id_country, id_holiday) values (2, 100029);
insert into t_monthfloatholidays(id_holiday, month, weekday, dayoffset) values(100029, 9, 0, 14);


delete from t_countryholidays where id_holiday = 51;
delete from t_monthfloatholidays where id_holiday = 51;
delete from t_holidays where _id = 51;

update t_holidays set title='день работников сельского хозяйства',
description = 'День работников сельского хозяйства — профессиональный праздник, который сельскохозяйственные работники Украины, Казахстана и Беларуси отмечают ежегодно в третье воскресенье ноября. Этот праздник существовал еще в Советском Союзе, где отмечался в этот же день.'
where _id = 222;

update t_holidays set day = 1, actualDateStr = '1 ноября',
description='День судебного пристава Российской Федерации отмечается ежегодно 1 ноября, начиная с 2009 года. Ранее, с 1997 по 2008 годы, российские судебные приставы России отмечали свой профессиональный праздник неофициально, приурочив его к дате 6 ноября, являющейся днём вступления в силу Федерального закона от 21 июля 1997 года «О судебных приставах» и Федерального закона от 21 июля 1997 года «Об исполнительном производстве».'
where _id = 221;

update t_holidays set description='Профессиональный праздник работников, занятых в торговле (в том числе розничной), тружеников сферы услуг, а также трудящихся, чьи специальности непосредственно связаны с ЖКХ. После обретения всеми пятнадцатью республиками СССР независимости друг от друга, сохранился в ряде государств СНГ. В некоторых странах «День работников торговли» и  «День работников ЖЭК» стали самостоятельными праздниками.',
title = 'день работников жилищно-коммунального хозяйства и бытового обслуживания населения'
where _id = 85;
insert into t_countryholidays(id_country, id_holiday) values (4, 85);

update t_holidays set actualDateStr = '21 ноября' where _id = 158;

update t_holidays set description = 'День мелиоратора установлен Указом Президиума Верховного совета СССР от 24 мая 1976 года. В задачи мелиорации, а, следовательно, и мелиораторов, входит создание благоприятных для сельского хозяйства условий: облагораживание почв, контроль и мониторинг водного, воздушного, теплового и пищевого режима почвы и режима ее влажности.' 
where _id = 461;
insert into t_countryholidays(id_country, id_holiday) values (3, 461);

update t_holidays set day=null, actualDateStr='Первое воскресенье ноября'
where _id = 337;
insert into t_monthfloatholidays(id_holiday, month, weekday, dayoffset) values(337, 10, 0, 0);

update t_holidays set description = 'Впервые праздник был утверждён Указом Президиума Верховного Совета СССР от 23 мая 1966 года в память о дне принятия Государственного Плана Электрификации России (ГОЭЛРО) на восьмом Всероссийском съезде Советов в 1920 году. Неофициальной датой празднования является 22 декабря.',
actualDateStr='Третье воскресенье декабря'
where _id = 258;
insert into t_monthfloatholidays(id_holiday, month, weekday, dayoffset) values(258, 11, 0, 14);
delete from t_countryholidays where id_holiday = 258 and id_country in (3, 4);

delete from t_countryholidays where id_holiday = 157;
delete from t_holidays where _id = 157;

delete from t_countryholidays where id_holiday = 228;
delete from t_holidays where _id = 228;

update t_holidays set title = 'всеукраинский день работников культуры и любителей народного искусства',
description = 'Первоначально этот День был установлен в марте 2000 года Указом президента Украины № 484/2000 и отмечался 23 марта. В 2011 году Указом президента дата празднования был перенесен на третье майское воскресенье.',
actualDateStr='Третье воскресенье мая', month = 4, day = null
where _id = 134;
insert into t_monthfloatholidays(id_holiday, month, weekday, dayoffset) values(134, 4, 0, 14);

update t_holidays set title = 'день инкассатора',
description = 'Дата празднования выбрана не случайно. 1 августа 1939 года на основании приказа председателя Правления Госбанка СССР была создана служба инкассации при Госбанке СССР.' 
where _id = 473;
insert into t_countryholidays(id_country, id_holiday) values (4, 473);

insert into t_countryholidays(id_country, id_holiday) values (4, 401);

update t_holidays set month=8, actualDateStr='17 сентября'
where _id = 211;

update t_holidays set month = 3, day = 17, actualDateStr = '17 апреля',
description = 'Дата празднования установлена указом президента Украины от 11.10.2013. Ранее работники пожарной охраны отмечали свой профессиональный праздник в День спасателя Украины 17 сентября вместе с сотрудниками Государственной службы по чрезвычайным ситуациям.' 
where _id = 181;

insert into t_countryholidays(id_country, id_holiday) values (4, 118);

insert into t_countryholidays(id_country, id_holiday) values (4, 59);

insert into t_countryholidays(id_country, id_holiday) values (3, 267);

delete from t_countryholidays where id_holiday = 87 and id_country = 3;
delete from t_countryholidays where id_holiday = 87 and id_country = 2;




