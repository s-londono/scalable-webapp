function renderItemRow(item) {
  return $("<tr><td>Mk.I</td><td>555</td></tr>");
}

function renderStockInContainer(stock, container) {
  var stockTable = $(
      "<table id='stockTable'>" +
        "<thead>" +
          "<tr><th>Item</th><th>In Stock</th></tr>" +
        "</thead>" +
        "<tbody></tbody>" +
      "</table>"
  );

  var stockTableBody = stockTable.find("tbody");

  stockTableBody.append(renderItemRow({}));

  container.append(stockTable);
}