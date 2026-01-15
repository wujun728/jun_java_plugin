#sql("findBlog")
  select * from blog where id = #para(0);
#end