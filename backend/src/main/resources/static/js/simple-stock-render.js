function renderStockInContainer(stock, container) {
  var stockTable = $(
      "<table id='stockTable'>" +
        "<thead>" +
          "<tr><th>Item</th><th>Model</th><th>In Stock</th></tr>" +
        "</thead>" +
        "<tbody></tbody>" +
      "</table>"
  );

  container.append(stockTable);
}

function renderItemStock(itemStock, sku) {
  var itemModel = _.get(itemStock, "item.model", "");
  var amntInStock = _.get(itemStock, "amountInStock", 0);

  var stkTblBody = $("#stockTable tbody");
  var itemRow = $(`<tr id="irow-${sku}"><td>${sku}</td><td>${itemModel}</td><td>${amntInStock}</td></tr>`);

  stkTblBody.append(itemRow);
}