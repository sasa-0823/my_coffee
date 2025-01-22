const main = () => {
  const input = document.getElementById("input_img")
  const preview = document.getElementById("preview")

  input.addEventListener("change", (event) => {
    const [file] = event.target.files

    if(file){
      preview.setAttribute('src', URL.createObjectURL(file))  //画面が更新されるまで画像のURLを保持
      preview.style.display = "block"
    }else{
      preview.style.display = "none"
    }
  })
}

main()