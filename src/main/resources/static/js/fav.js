
$(function () {

  $("form[id^='fov_']").on("submit", function (e) {
    e.preventDefault();
    console.log($(this).attr("action"));
    $.ajax({
      url: $(this).attr("action"),
      method: "post",
      cache: false,
      dataType: "json"
    }).done(function (favEnabled) {
      // fav:true / notfav:false
      console.log(favEnabled.isfav);
      $("this").attr("if", favEnabled.isFav)

    })
      .fail(function (favEnabled) {
        console.log(favEnabled.isfav);
        alert('送信エラー' + favEnabled);
      })
  })
});