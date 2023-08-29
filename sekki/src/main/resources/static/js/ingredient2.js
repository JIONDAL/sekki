/*IngreDient(음식재료의) CODE*/

document.addEventListener("DOMContentLoaded", function () {
  const fetchIngredientsButton = document.getElementById("fetchIngredientsButton");
  fetchIngredientsButton.addEventListener("click", () => {
    const selectedIngredients = document.getElementById("selectedIngredientsInput").value;
    // 서버로 선택한 재료를 전달하는 로직을 추가
    // (AJAX를 사용하여 서버에 데이터 전송 등)
    // 이후 서버에서 API를 호출하여 재료를 가져오는 로직을 추가하면 됨
  });
});

// 사용자의 재료 정보와 Edamam API에서 가져온 레시피의 재료 정보를 비교하여 보유한 재료인지 판단하는 함수
function shouldBuyIngredients(userIngredients, edamamIngredients) {
  // 사용자의 재료와 Edamam API에서 가져온 레시피의 재료를 각각 Set으로 변환
  const userIngredientsSet = new Set(userIngredients);
  const edamamIngredientsSet = new Set(edamamIngredients);

  // Edamam API에서 가져온 재료가 모두 사용자의 재료에 포함되면 재료를 보유한 것으로 간주
  return edamamIngredients.every(ingredient => userIngredientsSet.has(ingredient));
}

// 사용자의 재료 보유 여부를 판단하는 함수
async function checkUserIngredientStatus(id, searchQuery) {
  // 데이터베이스나 외부 API에서 사용자의 재료 정보를 가져오는 로직을 작성합니다.
  // 이 예제에서는 임시로 사용자의 재료 정보를 정적으로 지정합니다.
  const userIngredients = ["재료1", "재료2", "재료3"];

  // Edamam API를 호출하여 레시피의 재료 정보를 가져옵니다.
  const edamamData = await fetchIngredientsFromEdamam(searchQuery);
  const edamamIngredients = edamamData.hits.map(hit => hit.recipe.ingredientLines[0]);

  // 사용자의 재료와 Edamam API에서 가져온 레시피의 재료를 비교하여 재료 보유 여부를 판단합니다.
  const hasIngredients = shouldBuyIngredients(userIngredients, edamamIngredients);

  return hasIngredients;
}