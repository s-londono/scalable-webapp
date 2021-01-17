function renderStockInContainer(stock, container) {
  var stockTable = $(
      "<table id='stockTable'>" +
        "<thead>" +
          "<tr><th>Item</th><th>Model</th><th>In Stock</th><th></th></tr>" +
        "</thead>" +
        "<tbody></tbody>" +
      "</table>"
  );

  container.append(stockTable);
}

function renderItemStock(itemStock, onItemStockDelta) {
  var stkTblBody = $("#stockTable tbody");
  var itemRow = renderItemRow(itemStock, onItemStockDelta);

  stkTblBody.append(itemRow);
}

function renderItemStockUpdate(itemStock, onItemStockDelta) {
  if (itemStock == null) {
    return;
  }

  var sku = _.get(itemStock, "item.sku", "");
  var amntInStock = _.get(itemStock, "amountInStock", 0);

  var stkTblBody = $("#stockTable tbody");
  var rowId = renderEscapeSkuAsElId(sku);
  var newItemRow = renderItemRow(itemStock, onItemStockDelta);

  console.log("UPDATE ROW: %s -> %s", sku, amntInStock);

  stkTblBody.find(`#irow-${rowId}`).replaceWith(newItemRow);
}

function renderItemRow(itemStock, onItemStockDelta) {
  var sku = _.get(itemStock, "item.sku", "");
  var itemModel = _.get(itemStock, "item.model", "");
  var amntInStock = _.get(itemStock, "amountInStock", 0);
  var rowId = renderEscapeSkuAsElId(sku);

  var newItemRow = $(`<tr id="irow-${rowId}"></tr>`);
  newItemRow.append($(`<td>${sku}</td>`));
  newItemRow.append($(`<td>${itemModel}</td>`));
  newItemRow.append($(`<td>${amntInStock}</td>`));

  var btnAdd = $(`<button>+</button>`);
  var btnSub = $(`<button>-</button>`);
  btnAdd.bind("click", function() { onItemStockDelta(sku, 1); });
  btnSub.bind("click", function() { onItemStockDelta(sku, -1); });

  var btnsCell = $(`<td class="buttons-cell"></td>`);
  btnsCell.append(btnAdd);
  btnsCell.append(btnSub);

  newItemRow.append(btnsCell);

  return newItemRow;
}

function renderEscapeSkuAsElId(sku) {
  return sku.replace(/[.#\/,-;\\ ]+/gi, "");
}