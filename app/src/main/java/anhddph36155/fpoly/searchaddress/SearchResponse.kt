package anhddph36155.fpoly.searchaddress

data class SearchResponse(val items: List<AddressItem>)

data class AddressItem(
    val title: String,
    val address: Address
)

data class Address(val label: String)
