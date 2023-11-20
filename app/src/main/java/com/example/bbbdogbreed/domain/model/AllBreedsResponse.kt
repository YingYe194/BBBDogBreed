package com.example.bbbdogbreed.domain.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class AllBreedsResponse(
    @Json(name = "message")
    var breedAndAllBreed: AllBreed,

    @Json(name="status")
    var status: String
)

@JsonClass(generateAdapter = true)
data class AllBreed(
    @Json(name = "affenpinscher")
    var affenpinscher: List<String>,

    @Json(name = "african")
    var african: List<String>,

    @Json(name = "airedale")
    var airedale: List<String>,

    @Json(name = "akita")
    var akita: List<String>,

    @Json(name = "appenzeller")
    var appenzeller: List<String>,

    @Json(name = "australian")
    var australian: List<String>,

    @Json(name = "bakharwal")
    var bakharwal: List<String>,

    @Json(name = "basenji")
    var basenji: List<String>,

    @Json(name = "beagle")
    var beagle: List<String>,

    @Json(name = "bluetick")
    var bluetick: List<String>,

    @Json(name = "borzoi")
    var borzoi: List<String>,

    @Json(name = "bouvier")
    var bouvier: List<String>,

    @Json(name = "boxer")
    var boxer: List<String>,

    @Json(name = "brabancon")
    var brabancon: List<String>,

    @Json(name = "briard")
    var briard: List<String>,

    @Json(name = "buhund")
    var buhund: List<String>,

    @Json(name = "bulldog")
    var bulldog: List<String>,

    @Json(name = "bullterrier")
    var bullterrier: List<String>,

    @Json(name = "cattledog")
    var cattledog: List<String>,

    @Json(name = "cavapoo")
    var cavapoo: List<String>,

    @Json(name = "chihuahua")
    var chihuahua: List<String>,

    @Json(name = "chippiparai")
    var chippiparai: List<String>,

    @Json(name = "chow")
    var chow: List<String>,
)

/**
 * This is all breeds, we can use response from "https://dog.ceo/api/breeds/list/all" as an option in the select dog breed dropdown list,
 * But running out of time, I don't have it set up
 */
val AllBreeds = listOf(
        "affenpinscher",
        "african",
        "airedale",
        "akita",
        "appenzeller",
        "australian",
        "bakharwal",
        "basenji",
        "beagle",
        "bluetick",
        "borzoi",
        "bouvier",
        "boxer",
        "brabancon",
        "briard",
        "buhund",
        "bulldog",
        "bullterrier",
        "cattledog",
        "cavapoo",
        "chihuahua",
        "chippiparai",
        "chow",
        "clumber",
        "cockapoo",
        "collie",
        "coonhound",
        "corgi",
        "cotondetulear",
        "dachshund",
        "dalmatian",
        "dane",
        "deerhound",
        "dhole",
        "dingo",
        "doberman",
        "elkhound",
        "entlebucher",
        "eskimo",
        "finnish",
        "frise",
        "gaddi",
        "germanshepherd",
        "greyhound",
        "groenendael",
        "havanese",
        "hound",
        "husky",
        "keeshond",
        "kelpie",
        "kombai",
        "komondor",
        "kuvasz",
        "labradoodle",
        "labrador",
        "leonberg",
        "lhasa",
        "malamute",
        "malinois",
        "maltese",
        "mastiff",
        "mexicanhairless",
        "mix",
        "mountain",
        "mudhol",
        "newfoundland",
        "otterhound",
        "ovcharka",
        "papillon",
        "pariah",
        "pekinese",
        "pembroke",
        "pinscher",
        "pitbull",
        "pointer",
        "pomeranian",
        "poodle",
        "pug",
        "puggle",
        "pyrenees",
        "rajapalayam",
        "redbone",
        "retriever",
        "ridgeback",
        "rottweiler",
        "saluki",
        "samoyed",
        "schipperke",
        "schnauzer",
        "segugio",
        "setter",
        "sharpei",
        "sheepdog",
        "shiba",
        "shihtzu",
        "spaniel",
        "spitz",
        "springer",
        "stbernard",
        "terrier",
        "tervuren",
        "vizsla",
        "waterdog",
        "weimaraner",
        "whippet",
        "wolfhound"
)