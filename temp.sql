SELECT * FROM textcontext t where t.context like '%雅安%'

delete FROM textcontext


select current_date

select now();

select count(1) from textcontext where title = ''

-- MYSQL删除重复记录(正解） 不报You can't specify target table 'textcontext' for update in FROM clause
delete a FROM textcontext a ,(
  SELECT * FROM textcontext GROUP BY title  having count(1)>1
) b where a.title=b.title and a.recId > b.recId;


select count(1) from textcontext where titleUrl='/articles/f6zYfi';


insert into TextContext (fromUrl,title,titleUrl,context,imgLocUrl,imgUrl,status,type,gettedDt) values('http://www.tuicool.com/ah/','谷歌苹果Facebook甲骨文员工骑自行车去上班','/articles/m22mYr','
				腾讯科技讯（云松）北京时间4月21日消息，据国外媒体报道，虽然硅谷的科技公司为居住在旧金山的员工提供了豪华班车，但仍有部分员工愿意花一个半到三个小时的时间骑自行车去上班。谷歌、苹果、Facebook和甲骨文等大牌科技公司的员工还专门成立名为SF2G的骑行组织。
			','null','http://aimg0.tuicool.com/INVbA3.jpg',1,1,now());
