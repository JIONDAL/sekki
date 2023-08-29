/*IngreDient(음식재료의) CODE*/

  function fetchIngredients() {
	  
	  
    // edamam 레시피 API 요청을 보낼 URL
    
    const selectedIngredients = ["재료1", "재료2", "재료3"]; // 사용자가 선택한 재료 배열

    // 선택한 재료들을 쉼표(,)로 구분하여 검색 키워드로 만듦
    const searchQuery = selectedIngredients.join(",");

    const apiUrl = 'https://api.edamam.com/search?q=${searchQuery}&app_id=7d8f664a&app_key=a6f332294d947d0141a2d499a7ac1688';
    
    fetch(apiUrl)
      .then(response => response.json())
      .then(data => {
        // 재료 데이터를 가져와서 ingredientsList에 추가
        const ingredientsList = document.querySelector(".ingredients ul");
        data.hits.forEach(hit => {
          const ingredient = hit.recipe.ingredientLines[0];
          const listItem = document.createElement("li");
          listItem.textContent = ingredient;
          ingredientsList.appendChild(listItem);
        });
        // 재료가 없을 경우 구매 버튼 활성화
        if (data.hits.length === 0) {
          const buyButton = document.getElementById("buyButton");
          buyButton.disabled = false;
        }
      })
      .catch(error => {
        console.error('Error fetching ingredients:', error);
      });
    
    
  }
  // 페이지 로드 시 재료 데이터 가져오기
  fetchIngredients();