
$(function () {

  const rlId = $("button").attr("recipeData");

  $(`#ajax + rlId`).on("click", function () {
    $.ajax({
      url: "/Favorite/" + rlId,
      method: "post",
      cache    : false,
      dataType: "json"
    }).done(function (favEnabled) {
      // fav:true / notfav:false
      $("form").attr("if", favEnabled)

    })
      .fail(function (favEnabled) {
        alert('送信エラー');
      })
  })
});