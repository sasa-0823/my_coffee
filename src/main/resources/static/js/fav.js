
$(function(){

  const rlId = $("button").attr("recipeData");

  $(#ajax + rlId).on("click", function(){
    $.ajax({
      url:"/Favorite/" + rlId,
      method:"post",
      dataType:"json"
    }).done(function(favEnabled){
      $("form").attr("if")
    })
  })
})