package com.nammapride.app

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.webkit.CookieManager
import android.view.View
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.FrameLayout
import android.widget.ImageView
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.filled.Badge
import androidx.compose.material.icons.filled.Category
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.CollectionsBookmark
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.FastForward
import androidx.compose.material.icons.filled.FastRewind
import androidx.compose.material.icons.filled.Fullscreen
import androidx.compose.material.icons.filled.FullscreenExit
import androidx.compose.material.icons.filled.Groups
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Map
import androidx.compose.material.icons.filled.Navigation
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PhotoCamera
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.QrCodeScanner
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.Verified
import androidx.compose.material.icons.filled.VolumeOff
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material.icons.outlined.ErrorOutline
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.PlayerConstants
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.FullscreenListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.options.IFramePlayerOptions
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONArray
import org.json.JSONObject
import org.osmdroid.config.Configuration
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.CustomZoomButtonsController
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker
import java.net.URL
import java.util.Locale

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ChannapatnaTheme {
                ChannapatnaApp()
            }
        }
    }
}

private enum class Language { English, Kannada }
private enum class Screen { Home, Verify, Process, Catalog, Makers, Collection, Cart, Profile }
private const val FIREBASE_PROJECT_ID = "channapatnaapp-8b10a"
private const val FIREBASE_PACKAGE_NAME = "com.example.channapatna"

private val LocalUiContext = compositionLocalOf<android.content.Context> { error("No UI context") }

@Composable
private fun s(id: Int, vararg args: Any): String {
    val ctx = LocalUiContext.current
    return if (args.isEmpty()) ctx.getString(id) else ctx.getString(id, *args)
}

private fun android.content.Context.localizedContext(language: Language): android.content.Context {
    val locale = if (language == Language.Kannada) Locale("kn") else Locale.ENGLISH
    val updated = android.content.res.Configuration(resources.configuration)
    updated.setLocale(locale)
    updated.setLayoutDirection(locale)
    return createConfigurationContext(updated)
}

private data class Copy(
    val appName: String,
    val tagline: String,
    val heritageLine: String,
    val verify: String,
    val process: String,
    val catalog: String,
    val makers: String,
    val home: String,
    val searchCode: String,
    val verifyAction: String,
    val verifiedToy: String,
    val invalidInput: String,
    val emptyInput: String,
    val notFound: String,
    val batchHint: String,
    val certified: String,
    val vegetableDyes: String,
    val navigate: String,
    val specialties: String,
    val products: String,
    val story: String,
    val language: String,
    val featured: String,
    val trustTitle: String,
    val trustBody: String,
    val makerMap: String,
    val tapMarker: String,
    val processTitle: String,
    val processBody: String,
    val videoReady: String,
    val watchVideo: String = "Watch making video",
    val collection: String = "Collection",
    val myCollection: String = "My Collection",
    val addCollection: String = "Add Collection",
    val toyDescription: String = "Toy description",
    val price: String = "Price",
    val uploadJpg: String = "Upload JPG",
    val saveToy: String = "Save toy",
    val collectionEmpty: String = "No toys added yet. Add your first verified toy.",
    val requiredFields: String = "Toy ID, description, price, and JPG image are required."
)

private fun strings(language: Language): Copy = when (language) {
    Language.English -> Copy(
        appName = "Channapatna - Namma Pride",
        tagline = "Verify the toy. Meet the hands. Keep the craft alive.",
        heritageLine = "Authentic Channapatna wooden toys made from hale wood and finished with glossy natural lac dye.",
        verify = "Verify My Toy",
        process = "How It's Made",
        catalog = "Toy Catalog",
        makers = "Meet the Maker",
        home = "Home",
        searchCode = "Enter 6-digit Toy ID",
        verifyAction = "Verify authenticity",
        verifiedToy = "Authentic toy verified",
        invalidInput = "Toy ID must be exactly 6 digits.",
        emptyInput = "Enter the 6-digit Toy ID printed on your toy tag.",
        notFound = "No artisan record found for this Toy ID.",
        batchHint = "Try 100001, 100002, or 100003",
        certified = "Certified artisan",
        vegetableDyes = "Lac dye",
        navigate = "Navigate",
        specialties = "Specialties",
        products = "Verified wooden toys",
        story = "Maker story",
        language = "Language",
        featured = "Featured toys",
        trustTitle = "Fast local verification",
        trustBody = "A local offline data store maps each 6-digit Toy ID to one verified artisan profile, so lookup works even without internet.",
        makerMap = "Artisan map",
        tapMarker = "Tap a marker to view artisan details.",
        processTitle = "Hale wood and lac dye process",
        processBody = "Artisans season hale wood, turn each form on a hand lathe, warm natural lac sticks with mineral and vegetable pigments, and polish the toy until the surface becomes glossy, smooth, and child safe.",
        videoReady = "Watch a real Channapatna toy-making video from the app.",
        watchVideo = "Watch making video",
        collection = "Collection",
        myCollection = "My Collection",
        addCollection = "Add Collection",
        toyDescription = "Toy description",
        price = "Price",
        uploadJpg = "Upload JPG",
        saveToy = "Save toy",
        collectionEmpty = "No toys added yet. Add your first verified toy.",
        requiredFields = "Toy ID, description, price, and JPG image are required."
    )

    Language.Kannada -> Copy(
        appName = "ಚನ್ನಪಟ್ಟಣ - ನಮ್ಮ ಪ್ರೈಡ್",
        tagline = "ಆಟಿಕೆಯನ್ನು ಪರಿಶೀಲಿಸಿ. ಕಲಾವಿದರನ್ನು ಭೇಟಿ ಮಾಡಿ. ಕಲೆ ಉಳಿಸಿ.",
        heritageLine = "ಹಾಲೆ ಮರದಿಂದ ತಯಾರಿಸಿ ನೈಸರ್ಗಿಕ ಲಾಕ್ ಬಣ್ಣದಿಂದ ಪೂರ್ಣಗೊಳಿಸಿದ ನಿಜವಾದ ಚನ್ನಪಟ್ಟಣ ಮರದ ಆಟಿಕೆಗಳು.",
        verify = "ನನ್ನ ಆಟಿಕೆ ಪರಿಶೀಲಿಸಿ",
        process = "ಹೇಗೆ ತಯಾರಿಸಲಾಗುತ್ತದೆ",
        catalog = "ಆಟಿಕೆ ಪಟ್ಟಿ",
        makers = "ಕಲಾವಿದರನ್ನು ಭೇಟಿ ಮಾಡಿ",
        home = "ಮುಖಪುಟ",
        searchCode = "6 ಅಂಕಿಯ ಆಟಿಕೆ ID ನಮೂದಿಸಿ",
        verifyAction = "ಪ್ರಾಮಾಣಿಕತೆ ಪರಿಶೀಲಿಸಿ",
        verifiedToy = "ನಿಜವಾದ ಆಟಿಕೆ ಪರಿಶೀಲಿಸಲಾಗಿದೆ",
        invalidInput = "ಆಟಿಕೆ ID ಕಡ್ಡಾಯವಾಗಿ 6 ಅಂಕಿಗಳಾಗಿರಬೇಕು.",
        emptyInput = "ಆಟಿಕೆ ಟ್ಯಾಗ್ ಮೇಲೆ ಇರುವ 6 ಅಂಕಿಯ ID ನಮೂದಿಸಿ.",
        notFound = "ಈ ಆಟಿಕೆ IDಗೆ ಕಲಾವಿದರ ದಾಖಲೆ ಸಿಗಲಿಲ್ಲ.",
        batchHint = "100001, 100002, ಅಥವಾ 100003 ಪ್ರಯತ್ನಿಸಿ",
        certified = "ಪ್ರಮಾಣಿತ ಕಲಾವಿದ",
        vegetableDyes = "ಲಾಕ್ ಬಣ್ಣ",
        navigate = "ಮಾರ್ಗದರ್ಶನ",
        specialties = "ವಿಶೇಷತೆಗಳು",
        products = "ಪರಿಶೀಲಿತ ಮರದ ಆಟಿಕೆಗಳು",
        story = "ಕಲಾವಿದರ ಕಥೆ",
        language = "ಭಾಷೆ",
        featured = "ಪ್ರಮುಖ ಆಟಿಕೆಗಳು",
        trustTitle = "ವೇಗದ ಸ್ಥಳೀಯ ಪರಿಶೀಲನೆ",
        trustBody = "ಪ್ರತಿ 6 ಅಂಕಿಯ ಆಟಿಕೆ IDಗೆ ಒಂದೇ ಪ್ರಮಾಣಿತ ಕಲಾವಿದರ ಪ್ರೊಫೈಲ್ ಜೋಡಿಸಿರುವುದರಿಂದ, ಇಂಟರ್ನೆಟ್ ಇಲ್ಲದಿದ್ದರೂ ಹುಡುಕಾಟ ಕೆಲಸ ಮಾಡುತ್ತದೆ.",
        makerMap = "ಕಲಾವಿದರ ನಕ್ಷೆ",
        tapMarker = "ಕಲಾವಿದರ ವಿವರ ನೋಡಲು ಮಾರ್ಕರ್ ಒತ್ತಿ.",
        processTitle = "ಹಾಲೆ ಮರ ಮತ್ತು ಲಾಕ್ ಬಣ್ಣದ ವಿಧಾನ",
        processBody = "ಕಲಾವಿದರು ಹಾಲೆ ಮರವನ್ನು ಒಣಗಿಸಿ, ಕೈ ಲೇತ್‌ನಲ್ಲಿ ಆಕಾರ ನೀಡಿ, ನೈಸರ್ಗಿಕ ಲಾಕ್ ಕಡ್ಡಿಗಳನ್ನು ಬಿಸಿ ಮಾಡಿ ಬಣ್ಣ ಹಚ್ಚಿ, ಮಕ್ಕಳಿಗೆ ಸುರಕ್ಷಿತವಾದ ಮೃದುವಾದ ಹೊಳಪಿನ ಮಟ್ಟಕ್ಕೆ ಪಾಲಿಶ್ ಮಾಡುತ್ತಾರೆ.",
        videoReady = "ಆಪ್‌ನಲ್ಲೇ ನೈಜ ಚನ್ನಪಟ್ಟಣ ಆಟಿಕೆ ತಯಾರಿಕೆಯ ವಿಡಿಯೋ ನೋಡಿ.",
        watchVideo = "ತಯಾರಿಕೆ ವಿಡಿಯೋ ನೋಡಿ",
        collection = "ಸಂಗ್ರಹ",
        myCollection = "ನನ್ನ ಸಂಗ್ರಹ",
        addCollection = "ಸಂಗ್ರಹ ಸೇರಿಸಿ",
        toyDescription = "ಆಟಿಕೆಯ ವಿವರಣೆ",
        price = "ಬೆಲೆ",
        uploadJpg = "JPG ಅಪ್‌ಲೋಡ್ ಮಾಡಿ",
        saveToy = "ಆಟಿಕೆ ಉಳಿಸಿ",
        collectionEmpty = "ಇನ್ನೂ ಆಟಿಕೆಗಳನ್ನು ಸೇರಿಸಿಲ್ಲ. ನಿಮ್ಮ ಮೊದಲ ಪರಿಶೀಲಿತ ಆಟಿಕೆ ಸೇರಿಸಿ.",
        requiredFields = "ಆಟಿಕೆ ID, ವಿವರಣೆ, ಬೆಲೆ ಮತ್ತು JPG ಚಿತ್ರ ಕಡ್ಡಾಯ."
    )
}

private data class Toy(
    val id: String,
    val name: String,
    val kannadaName: String,
    val artisanId: String,
    val category: String,
    val price: String,
    val dye: String,
    val story: String,
    val imageRes: Int,
    val palette: List<Color>
)

private data class Artisan(
    val id: String,
    val name: String,
    val kannadaName: String,
    val studio: String,
    val years: Int,
    val specialty: String,
    val bio: String,
    val lat: Double,
    val lng: Double,
    val imageRes: Int
)

private data class CollectionToy(
    val toyId: String,
    val description: String,
    val price: String,
    val imageUri: String
)

private data class CartItem(
    val toyId: String,
    val toyName: String,
    val price: String,
    val quantity: Int
)

private data class CheckoutDetails(
    val address: String,
    val phone: String,
    val paymentMethod: String
)

private data class OrderItem(
    val orderId: String,
    val status: String,
    val total: Int,
    val createdAt: Long,
    val agentName: String,
    val agentPhone: String
)

private data class AppUser(
    val uid: String,
    val name: String,
    val email: String,
    val firebaseUser: FirebaseUser?
)

private data class CraftVideo(
    val title: String,
    val videoUrl: String
) {
    val videoId: String = extractYouTubeVideoId(videoUrl)
    val embedUrl: String = "https://www.youtube.com/embed/$videoId?playsinline=1&rel=0"
    val thumbnailUrl: String = "https://img.youtube.com/vi/$videoId/hqdefault.jpg"
}

private object HeritageRepository {
    val artisans = listOf(
        Artisan("A001", "Ramesh Kumar", "ರಮೇಶ್ ಕುಮಾರ್", "Sri Lakshmi Toys", 28, "Rocking horses and lacquer polishing", "Ramesh runs a family workshop known for balanced hand-turned forms and durable child-safe lac finishes.", 12.6510, 77.2090, R.drawable.artisan_ramesh),
        Artisan("A002", "Suresh Gowda", "ಸುರೇಶ್ ಗೌಡ", "Cauvery Crafts", 22, "Spinning tops and school sets", "Suresh trains young turners and keeps traditional color recipes documented for repeatable production quality.", 12.6505, 77.2065, R.drawable.artisan_suresh),
        Artisan("A003", "Manjunath", "ಮಂಜುನಾಥ್", "Heritage Toys Unit", 31, "Wooden puzzles and lathe toys", "Manjunath specializes in tight-tolerance puzzle blocks that are sanded smooth for toddlers and collectors.", 12.6522, 77.2102, R.drawable.artisan_manjunath),
        Artisan("A004", "Lakshmi Devi", "ಲಕ್ಷ್ಮಿ ದೇವಿ", "Eco Lac Toys", 19, "Natural lac dyes and infant-safe shapes", "Lakshmi leads a women-run unit focused on vegetable pigment blends, rounded profiles, and low-waste finishing.", 12.6498, 77.2078, R.drawable.artisan_lakshmi),
        Artisan("A005", "Rafiq Ahmed", "ರಫೀಕ್ ಅಹಮದ್", "Classic Wood Creations", 25, "Classic figurines and repairs", "Rafiq restores heirloom toys and builds new pieces with the same repairable joinery used by older workshops.", 12.6515, 77.2087, R.drawable.artisan_rafiq)
    )

    val toys = listOf(
        Toy("100001", "Rocking Horse", "ಆಡುವ ಕುದುರೆ", "A001", "Rocking Horse", "Rs. 1,250", "Turmeric yellow, lac red, leaf green", "A polished hale-wood rocking horse from Sri Lakshmi Toys, balanced for gentle motion.", R.drawable.toy_rocking_horse, listOf(Color(0xFFFFC107), Color(0xFFD32F2F), Color(0xFF388E3C))),
        Toy("100002", "Spinning Top", "ಬುಗುರಿ", "A002", "Spinning Top", "Rs. 180", "Red lac with green and yellow rings", "A fast-spinning classroom favorite turned by Cauvery Crafts with a replaceable wooden peg.", R.drawable.toy_spinning_top, listOf(Color(0xFFD32F2F), Color(0xFFFFC107), Color(0xFF388E3C))),
        Toy("100003", "Wooden Puzzle", "ಮರದ ಪಜಲ್", "A003", "Wooden Puzzle", "Rs. 640", "Low-VOC lac in primary toy colors", "Interlocking hale-wood pieces shaped by Heritage Toys Unit for early spatial learning.", R.drawable.toy_wooden_puzzle, listOf(Color(0xFF388E3C), Color(0xFFFFC107), Color(0xFFD32F2F))),
        Toy("100004", "Rainbow Rattle", "ಬಣ್ಣದ ರಾಟಲ್", "A004", "Wooden Puzzle", "Rs. 420", "Soft vegetable pigments sealed with lac", "An infant-safe rattle finished by Eco Lac Toys with rounded edges and a soft sound chamber.", R.drawable.toy_rattle, listOf(Color(0xFFFFC107), Color(0xFF388E3C), Color(0xFFD32F2F))),
        Toy("100005", "Classic Train", "ಮರದ ರೈಲು", "A005", "Rocking Horse", "Rs. 980", "Deep red lac with yellow wheel bands", "A repairable wooden train from Classic Wood Creations, made with pegged wheels and smooth pull-cord play.", R.drawable.toy_train, listOf(Color(0xFFD32F2F), Color(0xFF388E3C), Color(0xFFFFC107)))
    )

    val moreToys = listOf(
        Toy("100006", "Wooden Elephant Cart", "Wooden Elephant Cart", "A001", "Pull Along", "Rs. 1,150", "Green cart with red and yellow lac details", "A bright elephant cart with sturdy wooden wheels, made for pretend journeys and shelf display.", R.drawable.toy_elephant_cart, listOf(Color(0xFF388E3C), Color(0xFFD32F2F), Color(0xFFFFC107))),
        Toy("100007", "Dancing Peacock Toy", "Dancing Peacock Toy", "A004", "Decor Toy", "Rs. 780", "Peacock green with yellow feather circles", "A hand-turned peacock toy with a balanced dancing base and classic Channapatna lac finish.", R.drawable.toy_dancing_peacock, listOf(Color(0xFF388E3C), Color(0xFFFFC107), Color(0xFFD32F2F))),
        Toy("100008", "Wooden Mini Kitchen Set", "Wooden Mini Kitchen Set", "A003", "Pretend Play", "Rs. 890", "Red vessels with yellow lids and green tray", "A child-friendly kitchen set with smooth cups, pots, and tray pieces for pretend play.", R.drawable.toy_kitchen_set, listOf(Color(0xFFD32F2F), Color(0xFFFFC107), Color(0xFF388E3C))),
        Toy("100009", "Pull Along Duck Toy", "Pull Along Duck Toy", "A002", "Pull Along", "Rs. 520", "Yellow duck with red beak and green base", "A cheerful pull-along duck with rounded wheels and a light string for toddler play.", R.drawable.toy_pull_duck, listOf(Color(0xFFFFC107), Color(0xFFD32F2F), Color(0xFF388E3C))),
        Toy("100010", "Wooden Ring Stacker", "Wooden Ring Stacker", "A004", "Learning Toy", "Rs. 640", "Stacked red, green, and yellow rings", "A simple learning toy that helps children sort sizes while enjoying natural wooden textures.", R.drawable.toy_ring_stacker, listOf(Color(0xFFD32F2F), Color(0xFF388E3C), Color(0xFFFFC107))),
        Toy("100011", "Handcrafted Baby Cradle Toy", "Handcrafted Baby Cradle Toy", "A005", "Decor Toy", "Rs. 1,350", "Red cradle with hale-wood supports", "A decorative miniature cradle made with smooth edges, warm colors, and traditional craft details.", R.drawable.toy_baby_cradle, listOf(Color(0xFFD32F2F), Color(0xFF8D5524), Color(0xFFFFC107)))
    )

    val allToys = toys + moreToys

    private val toyById = allToys.associateBy { it.id }
    private val artisanById = artisans.associateBy { it.id }

    fun verify(code: String): VerificationResult {
        val normalized = code.trim()
        if (normalized.isEmpty()) return VerificationResult.EmptyInput
        if (!normalized.matches(Regex("\\d{6}"))) return VerificationResult.InvalidInput
        val toy = toyById[normalized] ?: return VerificationResult.NotFound
        val artisan = artisanById.getValue(toy.artisanId)
        return VerificationResult.ToyRecord(toy, artisan)
    }
}

private val craftVideos = listOf(
    CraftVideo("Craft Making", "https://www.youtube.com/watch?v=lT9msXzHSYw"),
    CraftVideo("The Turning Colour Story", "https://youtu.be/84-LjWWn75Y"),
    CraftVideo("Channapatna Wooden Toys", "https://youtu.be/F9sTdXBi9Pk"),
    CraftVideo("Hand Made Toys Shop", "https://www.youtube.com/watch?v=EH3hwtJReXI")
)

private sealed interface VerificationResult {
    data class ToyRecord(val toy: Toy, val artisan: Artisan) : VerificationResult
    data object EmptyInput : VerificationResult
    data object InvalidInput : VerificationResult
    data object NotFound : VerificationResult
}

@Composable
private fun ChannapatnaTheme(content: @Composable () -> Unit) {
    val scheme = androidx.compose.material3.lightColorScheme(
        primary = Color(0xFF388E3C),
        onPrimary = Color.White,
        secondary = Color(0xFFD32F2F),
        tertiary = Color(0xFFFFC107),
        background = Color(0xFFFFF8E1),
        surface = Color(0xFFFFFCF4),
        surfaceVariant = Color(0xFFFFECB3),
        onSurface = Color(0xFF241B12),
        outline = Color(0xFF74685E)
    )
    MaterialTheme(colorScheme = scheme, content = content)
}

@Composable
private fun ChannapatnaApp() {
    val context = LocalContext.current
    val firebaseReady = remember { runCatching { FirebaseApp.initializeApp(context) != null }.getOrDefault(false) }
    val auth = remember(firebaseReady) { if (firebaseReady) FirebaseAuth.getInstance() else null }
    val firestore = remember(firebaseReady) { if (firebaseReady) FirebaseFirestore.getInstance() else null }
    var currentUser by remember { mutableStateOf<AppUser?>(null) }
    var authChecked by remember { mutableStateOf(false) }
    var language by rememberSaveable { mutableStateOf(Language.English) }
    var screen by rememberSaveable { mutableStateOf(Screen.Home) }
    var collectionToys by remember { mutableStateOf(context.loadCollectionToys()) }
    var cartItems by remember { mutableStateOf<List<CartItem>>(emptyList()) }
    var orders by remember { mutableStateOf<List<OrderItem>>(emptyList()) }
    val uiContext = remember(language) { context.localizedContext(language) }
    val copy = strings(language)

    CompositionLocalProvider(LocalUiContext provides uiContext) {
        LaunchedEffect(auth, firestore) {
        val firebaseUser = auth?.currentUser
        if (firebaseUser == null || firestore == null) {
            authChecked = true
        } else {
            firestore.loadUserProfile(
                user = firebaseUser,
                onSuccess = {
                    currentUser = it
                    authChecked = true
                },
                onFailure = {
                    auth.signOut()
                    currentUser = null
                    authChecked = true
                }
            )
        }
    }

    DisposableEffect(currentUser, firestore) {
        val user = currentUser
        if (user == null) {
            cartItems = emptyList()
            return@DisposableEffect onDispose { }
        }
        if (firestore == null) return@DisposableEffect onDispose { }
        // Listen to the logged-in user's cart in Firestore.
        val registration = firestore.collection("cart")
            .document(user.uid)
            .collection("items")
            .addSnapshotListener { snapshot, _ ->
                cartItems = snapshot?.documents?.mapNotNull { document ->
                    CartItem(
                        toyId = document.id,
                        toyName = document.getString("toyName").orEmpty(),
                        price = document.getString("price").orEmpty(),
                      0  quantity = document.getLong("quantity")?.toInt() ?: 1
                    )
                }.orEmpty()
            }
        onDispose { registration.remove() }
    }

    DisposableEffect(currentUser, firestore) {
        val user = currentUser
        if (user == null) {
            orders = emptyList()
            return@DisposableEffect onDispose { }
        }
        if (firestore == null) return@DisposableEffect onDispose { }
        val registration = firestore.collection("orders")
            .document(user.uid)
            .collection("items")
            .addSnapshotListener { snapshot, _ ->
                orders = snapshot?.documents?.map { document ->
                    OrderItem(
                        orderId = document.getString("orderId") ?: document.id,
                        status = document.getString("status") ?: "Placed",
                        total = document.getLong("total")?.toInt() ?: 0,
                        createdAt = document.getLong("createdAt") ?: 0L,
                        agentName = document.getString("agentName") ?: "Ravi Kumar",
                        agentPhone = document.getString("agentPhone") ?: "98765 43210"
                    )
                }?.sortedByDescending { it.createdAt }.orEmpty()
            }
        onDispose { registration.remove() }
    }

        if (auth == null || firestore == null) {
            FirebaseSetupRequired()
        } else if (!authChecked) {
            LoadingScreen()
        } else if (currentUser == null) {
            AuthScreen(
                auth = auth,
                firestore = firestore,
                onAuthSuccess = { user ->
                    currentUser = user
                    screen = Screen.Home
                }
            )
        } else {
            Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        bottomBar = {
            NavigationBar(
                containerColor = MaterialTheme.colorScheme.surface,
                tonalElevation = 8.dp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(WindowInsets.navigationBars.asPaddingValues())
            ) {
                navItems(copy).forEach { item ->
                    NavigationBarItem(
                        selected = screen == item.screen,
                        onClick = { screen = item.screen },
                        icon = { Icon(item.icon, contentDescription = item.label) },
                        label = { Text(item.label, maxLines = 1, overflow = TextOverflow.Ellipsis) }
                    )
                }
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(WindowInsets.statusBars.asPaddingValues())
        ) {
            TopBar(
                copy = copy,
                language = language,
                showBack = screen != Screen.Home,
                onBack = { screen = Screen.Home }
            ) {
                language = if (language == Language.English) Language.Kannada else Language.English
            }
            AnimatedContent(targetState = screen, label = "screen") { current ->
                when (current) {
                    Screen.Home -> HomeScreen(
                        copy = copy,
                        language = language,
                        onVerify = { screen = Screen.Verify },
                        onCatalog = { screen = Screen.Catalog },
                        onProcess = { screen = Screen.Process },
                        onMakers = { screen = Screen.Makers },
                        onCollection = { screen = Screen.Collection }
                    )
                    Screen.Verify -> VerifyScreen(copy, language)
                    Screen.Process -> ProcessScreen(copy)
                    Screen.Catalog -> CatalogScreen(
                        copy = copy,
                        language = language,
                        onAddToCart = { toy ->
                            val user = currentUser ?: return@CatalogScreen
                            firestore.addToyToCart(user.uid, toy)
                        },
                        onBuyNow = { toy ->
                            val user = currentUser ?: return@CatalogScreen
                            firestore.addToyToCart(user.uid, toy)
                            screen = Screen.Cart
                        }
                    )
                    Screen.Makers -> MakersScreen(copy, language)
                    Screen.Collection -> CollectionScreen(
                        copy = copy,
                        items = collectionToys,
                        onAdd = {
                            val updated = collectionToys + it
                            collectionToys = updated
                            context.saveCollectionToys(updated)
                        }
                    )
                    Screen.Cart -> CartScreen(
                        cartItems = cartItems,
                        onQuantityChange = { item, quantity ->
                            val user = currentUser ?: return@CartScreen
                            firestore.updateCartQuantity(user.uid, item.toyId, quantity)
                        },
                        onRemove = { item ->
                            val user = currentUser ?: return@CartScreen
                            firestore.removeCartItem(user.uid, item.toyId)
                        },
                        onCheckout = { details, showMessage ->
                            val user = currentUser ?: return@CartScreen
                            firestore.placeOrder(user.uid, cartItems, details, showMessage)
                        }
                    )
                    Screen.Profile -> ProfileScreen(
                        user = currentUser!!,
                        orders = orders,
                        onLogout = {
                            auth.signOut()
                            currentUser = null
                            cartItems = emptyList()
                            orders = emptyList()
                            screen = Screen.Home
                        }
                    )
                }
            }
        }
    }
}
    }
}

private data class NavItem(val screen: Screen, val label: String, val icon: ImageVector)

private fun navItems(copy: Copy) = listOf(
    NavItem(Screen.Home, copy.home, Icons.Filled.Home),
    NavItem(Screen.Verify, "Verify Toy", Icons.Filled.Verified),
    NavItem(Screen.Cart, "Cart", Icons.Filled.ShoppingCart),
    NavItem(Screen.Profile, "Profile", Icons.Filled.Person)
)

@Composable
private fun LoadingScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        contentAlignment = Alignment.Center
    ) {
        Text(s(R.string.loading), color = MaterialTheme.colorScheme.outline)
    }
}

@Composable
private fun FirebaseSetupRequired() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(24.dp),
        contentAlignment = Alignment.Center
    ) {
        Card(shape = RoundedCornerShape(8.dp), colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)) {
            Column(Modifier.padding(20.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                Text(s(R.string.firebase_setup_required), fontWeight = FontWeight.Black, fontSize = 22.sp)
                Text("${s(R.string.firebase_setup_required_body)}\n\nProject: $FIREBASE_PROJECT_ID\nPackage: $FIREBASE_PACKAGE_NAME")
            }
        }
    }
}

@Composable
private fun AuthScreen(auth: FirebaseAuth, firestore: FirebaseFirestore, onAuthSuccess: (AppUser) -> Unit) {
    var showSignup by rememberSaveable { mutableStateOf(false) }
    var authMessage by rememberSaveable { mutableStateOf<String?>(null) }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(22.dp),
        contentAlignment = Alignment.Center
    ) {
        ElevatedCard(shape = RoundedCornerShape(8.dp), colors = CardDefaults.elevatedCardColors(containerColor = MaterialTheme.colorScheme.surface)) {
            Column(Modifier.padding(20.dp), verticalArrangement = Arrangement.spacedBy(14.dp)) {
                ToyMark(Modifier.size(54.dp))
                Text(s(R.string.app_name_full), fontWeight = FontWeight.Black, fontSize = 24.sp)
                Text(if (showSignup) s(R.string.auth_create_account) else s(R.string.auth_login_to_continue), color = MaterialTheme.colorScheme.outline)
                authMessage?.let { Text(it, color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.SemiBold) }
                if (showSignup) {
                    SignupForm(
                        auth = auth,
                        firestore = firestore,
                        onSignupComplete = {
                            authMessage = "Signup successful. Please login with your email and password."
                            showSignup = false
                        }
                    )
                } else {
                    LoginForm(auth = auth, firestore = firestore, onAuthSuccess = onAuthSuccess)
                }
                TextButton(onClick = {
                    authMessage = null
                    showSignup = !showSignup
                }) {
                    Text(if (showSignup) s(R.string.auth_already_have) else s(R.string.auth_new_user))
                }
            }
        }
    }
}

@Composable
private fun LoginForm(auth: FirebaseAuth, firestore: FirebaseFirestore, onAuthSuccess: (AppUser) -> Unit) {
    var email by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    var message by rememberSaveable { mutableStateOf<String?>(null) }
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        OutlinedTextField(
            value = email,
            onValueChange = { email = it.trim() },
            label = { Text(s(R.string.field_email)) },
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text(s(R.string.field_password)) },
            singleLine = true,
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            modifier = Modifier.fillMaxWidth()
        )
        message?.let { Text(it, color = MaterialTheme.colorScheme.secondary, fontWeight = FontWeight.SemiBold) }
        Button(
            onClick = {
                message = validateLogin(email, password)
                if (message == null) {
                    // Firebase Authentication keeps the user logged in until logout.
                    auth.signInWithEmailAndPassword(email, password)
                        .addOnSuccessListener { result ->
                            val user = result.user
                            if (user == null) {
                                message = "Login failed. Please try again."
                            } else {
                                firestore.loadUserProfile(
                                    user = user,
                                    onSuccess = onAuthSuccess,
                                    onFailure = {
                                        auth.signOut()
                                        message = "Profile not found. Please signup first."
                                    }
                                )
                            }
                        }
                        .addOnFailureListener {
                            message = it.toFriendlyFirebaseMessage("Login failed. Please check email and password.")
                        }
                }
            },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(8.dp)
        ) {
            Text(s(R.string.action_login))
        }
    }
}

@Composable
private fun SignupForm(auth: FirebaseAuth, firestore: FirebaseFirestore, onSignupComplete: () -> Unit) {
    var name by rememberSaveable { mutableStateOf("") }
    var email by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    var confirmPassword by rememberSaveable { mutableStateOf("") }
    var message by rememberSaveable { mutableStateOf<String?>(null) }
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        OutlinedTextField(value = name, onValueChange = { name = it }, label = { Text(s(R.string.field_name)) }, singleLine = true, modifier = Modifier.fillMaxWidth())
        OutlinedTextField(value = email, onValueChange = { email = it.trim() }, label = { Text(s(R.string.field_email)) }, singleLine = true, keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email), modifier = Modifier.fillMaxWidth())
        OutlinedTextField(value = password, onValueChange = { password = it }, label = { Text(s(R.string.field_password)) }, singleLine = true, visualTransformation = PasswordVisualTransformation(), keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password), modifier = Modifier.fillMaxWidth())
        OutlinedTextField(value = confirmPassword, onValueChange = { confirmPassword = it }, label = { Text(s(R.string.field_confirm_password)) }, singleLine = true, visualTransformation = PasswordVisualTransformation(), keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password), modifier = Modifier.fillMaxWidth())
        message?.let { Text(it, color = MaterialTheme.colorScheme.secondary, fontWeight = FontWeight.SemiBold) }
        Button(
            onClick = {
                message = validateSignup(name, email, password, confirmPassword)
                if (message == null) {
                    // Create the Firebase Auth account, then save a simple user profile.
                    auth.createUserWithEmailAndPassword(email, password)
                        .addOnSuccessListener { result ->
                            val uid = result.user?.uid ?: return@addOnSuccessListener
                            val cleanName = name.trim()
                            val userData = mapOf(
                                "uid" to uid,
                                "name" to cleanName,
                                "email" to email,
                                "createdAt" to System.currentTimeMillis()
                            )
                            firestore.collection("users").document(uid).set(userData, SetOptions.merge())
                                .addOnSuccessListener {
                                    auth.signOut()
                                    onSignupComplete()
                                }
                                .addOnFailureListener {
                                    auth.signOut()
                                    message = it.toFriendlyFirebaseMessage("Signup created, but profile save failed.")
                                }
                        }
                        .addOnFailureListener {
                            message = it.toFriendlyFirebaseMessage("Signup failed. Please try again.")
                        }
                }
            },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(8.dp)
        ) {
            Text(s(R.string.action_signup))
        }
    }
}

@Composable
private fun TopBar(copy: Copy, language: Language, showBack: Boolean, onBack: () -> Unit, onLanguageChange: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 18.dp, vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (showBack) {
            IconButton(onClick = onBack) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
            }
            Spacer(Modifier.width(2.dp))
        }
        ToyMark(modifier = Modifier.size(42.dp))
        Spacer(Modifier.width(12.dp))
        Column(Modifier.weight(1f)) {
            Text(copy.appName, fontWeight = FontWeight.Black, fontSize = 18.sp, maxLines = 1, overflow = TextOverflow.Ellipsis)
            Text(copy.tagline, color = MaterialTheme.colorScheme.outline, fontSize = 12.sp, maxLines = 1, overflow = TextOverflow.Ellipsis)
        }
        IconButton(onClick = onLanguageChange) {
            Icon(Icons.Filled.Language, contentDescription = copy.language)
        }
        Text(if (language == Language.English) "EN" else "ಕ", fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
    }
}

@Composable
private fun HomeScreen(
    copy: Copy,
    language: Language,
    onVerify: () -> Unit,
    onCatalog: () -> Unit,
    onProcess: () -> Unit,
    onMakers: () -> Unit,
    onCollection: () -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(20.dp),
        verticalArrangement = Arrangement.spacedBy(18.dp)
    ) {
        item { HeroPanel(copy, onVerify, onCatalog) }
        item { HomeMenu(copy, onCatalog, onProcess, onMakers, onCollection) }
        item { TrustPanel(copy) }
        item { Text(copy.featured, fontWeight = FontWeight.Black, fontSize = 20.sp) }
        items(HeritageRepository.allToys.take(4)) { toy -> ProductCard(toy, copy, language) }
    }
}

@Composable
private fun HomeMenu(
    copy: Copy,
    onCatalog: () -> Unit,
    onProcess: () -> Unit,
    onMakers: () -> Unit,
    onCollection: () -> Unit
) {
    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
        Text(s(R.string.menu_title), fontWeight = FontWeight.Black, fontSize = 20.sp)
        MenuButton(copy.catalog, Icons.Filled.Category, onCatalog)
        MenuButton(copy.process, Icons.Filled.CheckCircle, onProcess)
        MenuButton(copy.makers, Icons.Filled.Groups, onMakers)
        MenuButton(copy.collection, Icons.Filled.CollectionsBookmark, onCollection)
    }
}

@Composable
private fun MenuButton(label: String, icon: ImageVector, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 14.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(icon, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
            Spacer(Modifier.width(12.dp))
            Text(label, modifier = Modifier.weight(1f), fontWeight = FontWeight.SemiBold)
            Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = null, modifier = Modifier.size(18.dp))
        }
    }
}

@Composable
private fun HeroPanel(copy: Copy, onVerify: () -> Unit, onCatalog: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(282.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(Brush.linearGradient(listOf(Color(0xFFFFC107), Color(0xFFD32F2F), Color(0xFF388E3C))))
    ) {
        Canvas(Modifier.fillMaxSize()) { drawToyPattern() }
        Column(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(22.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            Text(copy.appName, color = Color.White, fontWeight = FontWeight.Black, fontSize = 30.sp, lineHeight = 34.sp)
            Text(copy.heritageLine, color = Color.White.copy(alpha = 0.94f), fontSize = 15.sp, lineHeight = 20.sp)
            Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                Button(onClick = onVerify, colors = ButtonDefaults.buttonColors(containerColor = Color.White, contentColor = Color(0xFF388E3C)), shape = RoundedCornerShape(8.dp)) {
                    Icon(Icons.Filled.QrCodeScanner, contentDescription = null, modifier = Modifier.size(18.dp))
                    Spacer(Modifier.width(8.dp))
                    Text(copy.verify)
                }
                Button(onClick = onCatalog, colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF241B12), contentColor = Color.White), shape = RoundedCornerShape(8.dp)) {
                    Text(copy.catalog)
                    Spacer(Modifier.width(6.dp))
                    Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = null, modifier = Modifier.size(18.dp))
                }
            }
        }
    }
}

private fun DrawScope.drawToyPattern() {
    repeat(8) { index ->
        val center = Offset(size.width * (0.08f + index * 0.13f), size.height * (0.12f + (index % 3) * 0.16f))
        rotate(degrees = index * 18f, pivot = center) {
            drawOval(Color.White.copy(alpha = 0.18f), topLeft = center - Offset(42f, 18f), size = Size(84f, 36f))
            drawCircle(Color.White.copy(alpha = 0.22f), radius = 18f, center = center)
        }
    }
    val path = Path().apply {
        moveTo(0f, size.height * 0.74f)
        cubicTo(size.width * 0.25f, size.height * 0.60f, size.width * 0.48f, size.height, size.width, size.height * 0.78f)
        lineTo(size.width, size.height)
        lineTo(0f, size.height)
        close()
    }
    drawPath(path, Color(0xFF241B12).copy(alpha = 0.24f))
}

@Composable
private fun TrustPanel(copy: Copy) {
    ElevatedCard(shape = RoundedCornerShape(8.dp), elevation = CardDefaults.elevatedCardElevation(6.dp), colors = CardDefaults.elevatedCardColors(containerColor = MaterialTheme.colorScheme.surface)) {
        Row(Modifier.padding(18.dp), verticalAlignment = Alignment.CenterVertically) {
            Icon(Icons.Filled.CheckCircle, contentDescription = null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(44.dp))
            Spacer(Modifier.width(16.dp))
            Column {
                Text(copy.trustTitle, fontWeight = FontWeight.Bold, fontSize = 18.sp)
                Text(copy.trustBody, color = MaterialTheme.colorScheme.outline, lineHeight = 19.sp)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun VerifyScreen(copy: Copy, language: Language) {
    var code by rememberSaveable { mutableStateOf("") }
    var result by remember { mutableStateOf<VerificationResult?>(null) }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(20.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Text(copy.verify, fontSize = 28.sp, fontWeight = FontWeight.Black)
            Text(copy.batchHint, color = MaterialTheme.colorScheme.outline)
        }
        item {
            OutlinedTextField(
                value = code,
                onValueChange = { input ->
                    code = input.filter(Char::isDigit).take(6)
                    result = null
                },
                label = { Text(copy.searchCode) },
                leadingIcon = { Icon(Icons.Filled.Search, contentDescription = null) },
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword),
                isError = result is VerificationResult.EmptyInput || result is VerificationResult.InvalidInput || result is VerificationResult.NotFound,
                supportingText = { Text(copy.batchHint) },
                modifier = Modifier.fillMaxWidth()
            )
        }
        item {
            Button(
                onClick = { result = HeritageRepository.verify(code) },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(8.dp)
            ) {
                Icon(Icons.Filled.Verified, contentDescription = null)
                Spacer(Modifier.width(8.dp))
                Text(copy.verifyAction)
            }
        }
        item { result?.let { VerificationResultCard(it, copy, language) } }
    }
}

@Composable
private fun VerificationResultCard(result: VerificationResult, copy: Copy, language: Language) {
    val isError = result !is VerificationResult.ToyRecord
    val color by animateColorAsState(targetValue = if (isError) MaterialTheme.colorScheme.secondary else MaterialTheme.colorScheme.primary, label = "verificationColor")
    Card(shape = RoundedCornerShape(8.dp), elevation = CardDefaults.cardElevation(4.dp), colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)) {
        Column(Modifier.padding(18.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(if (isError) Icons.Outlined.ErrorOutline else Icons.Outlined.CheckCircle, contentDescription = null, tint = color, modifier = Modifier.size(34.dp))
                Spacer(Modifier.width(10.dp))
                Text(
                    text = when (result) {
                        is VerificationResult.ToyRecord -> copy.verifiedToy
                        VerificationResult.EmptyInput -> copy.emptyInput
                        VerificationResult.InvalidInput -> copy.invalidInput
                        VerificationResult.NotFound -> copy.notFound
                    },
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )
            }
            if (result is VerificationResult.ToyRecord) {
                ProductCard(result.toy, copy, language)
                ArtisanCard(result.artisan, copy, language)
            }
        }
    }
}

@Composable
private fun ProcessScreen(copy: Copy) {
    val context = LocalContext.current
    var playingVideo by remember { mutableStateOf<CraftVideo?>(null) }
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(20.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Text(copy.process, fontSize = 28.sp, fontWeight = FontWeight.Black)
            Text(copy.processTitle, color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.SemiBold)
        }
        item {
            Image(
                painter = painterResource(R.drawable.process_lac_dye),
                contentDescription = copy.processTitle,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(210.dp)
                    .clip(RoundedCornerShape(8.dp))
            )
        }
        item {
            ElevatedCard(shape = RoundedCornerShape(8.dp), colors = CardDefaults.elevatedCardColors(containerColor = MaterialTheme.colorScheme.surface)) {
                Column(Modifier.padding(18.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    Text(copy.processTitle, fontWeight = FontWeight.Bold, fontSize = 20.sp)
                    Text(copy.processBody, color = MaterialTheme.colorScheme.outline, lineHeight = 20.sp)
                    Text(copy.videoReady, color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.SemiBold)
                }
            }
        }
        item {
            Text(s(R.string.watch_header), fontWeight = FontWeight.Black, fontSize = 22.sp)
        }
        items(craftVideos) { video ->
            CraftVideoCard(
                video = video,
                onPlay = { playingVideo = video },
                onOpenExternal = { context.openYouTubeVideo(video.videoUrl) }
            )
        }
    }
    playingVideo?.let { video ->
        YouTubePlayerDialog(video = video, onDismiss = { playingVideo = null })
    }
}

@Composable
private fun CraftVideoCard(video: CraftVideo, onPlay: () -> Unit, onOpenExternal: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(5.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(16f / 9f)
                    .clip(RoundedCornerShape(8.dp))
                    .clickable(onClick = onPlay)
                    .background(Brush.linearGradient(listOf(Color(0xFFFFC107), Color(0xFFD32F2F), Color(0xFF388E3C))))
            ) {
                YouTubeThumbnail(video.thumbnailUrl, video.title)
                Box(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .size(62.dp)
                        .clip(CircleShape)
                        .background(Color.White.copy(alpha = 0.92f)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(Icons.Filled.PlayArrow, contentDescription = s(R.string.play_icon_desc), tint = Color(0xFFD32F2F), modifier = Modifier.size(38.dp))
                }
            }
            Column(Modifier.padding(horizontal = 16.dp, vertical = 4.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                Text(video.title, fontWeight = FontWeight.Bold, fontSize = 19.sp)
                Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    Button(onClick = onPlay, shape = RoundedCornerShape(8.dp)) {
                        Icon(Icons.Filled.PlayArrow, contentDescription = null, modifier = Modifier.size(18.dp))
                        Spacer(Modifier.width(8.dp))
                        Text(s(R.string.play_in_app))
                    }
                    TextButton(onClick = onOpenExternal) {
                        Icon(Icons.Filled.PlayArrow, contentDescription = null, modifier = Modifier.size(18.dp))
                        Spacer(Modifier.width(8.dp))
                        Text(s(R.string.open_in_youtube))
                    }
                }
            }
        }
    }
}

@Composable
private fun YouTubeThumbnail(thumbnailUrl: String, title: String) {
    var bitmap by remember(thumbnailUrl) { mutableStateOf<Bitmap?>(null) }
    var failed by remember(thumbnailUrl) { mutableStateOf(false) }

    LaunchedEffect(thumbnailUrl) {
        runCatching {
            withContext(Dispatchers.IO) {
                URL(thumbnailUrl).openStream().use(BitmapFactory::decodeStream)
            }
        }.onSuccess {
            bitmap = it
        }.onFailure {
            failed = true
        }
    }

    val loaded = bitmap
    when {
        loaded != null -> Image(
            bitmap = loaded.asImageBitmap(),
            contentDescription = title,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
        failed -> Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text(s(R.string.check_connection), color = Color.White, fontWeight = FontWeight.Bold)
        }
        else -> Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text(s(R.string.loading_preview), color = Color.White, fontWeight = FontWeight.Bold)
        }
    }
}

@Composable
private fun YouTubePlayerDialog(video: CraftVideo, onDismiss: () -> Unit) {
    var startPlayback by remember(video.videoId) { mutableStateOf(false) }
    var loadFailed by remember(video.videoId) { mutableStateOf(false) }
    var loading by remember(video.videoId) { mutableStateOf(false) }
    var player by remember(video.videoId) { mutableStateOf<YouTubePlayer?>(null) }
    var playerState by remember(video.videoId) { mutableStateOf(PlayerConstants.PlayerState.UNKNOWN) }
    var currentSecond by remember(video.videoId) { mutableStateOf(0f) }
    var duration by remember(video.videoId) { mutableStateOf(0f) }
    var muted by remember(video.videoId) { mutableStateOf(false) }
    var volume by remember(video.videoId) { mutableStateOf(100f) }
    var immersive by remember(video.videoId) { mutableStateOf(false) }
    var forceWebEmbed by remember(video.videoId) { mutableStateOf(false) }
    val showControls = startPlayback && !loadFailed && player != null && playerState != PlayerConstants.PlayerState.UNKNOWN
    Dialog(onDismissRequest = onDismiss) {
        Card(
            modifier = if (immersive) {
                Modifier
                    .fillMaxSize()
                    .padding(8.dp)
            } else {
                Modifier
                    .fillMaxWidth(0.94f)
                    .widthIn(max = 760.dp)
            },
            shape = RoundedCornerShape(8.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
        ) {
            Column(Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                Text(video.title, fontWeight = FontWeight.Black, fontSize = 20.sp)
                if (!startPlayback) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .aspectRatio(16f / 9f)
                            .clip(RoundedCornerShape(8.dp))
                            .background(MaterialTheme.colorScheme.surfaceVariant)
                            .clickable {
                                startPlayback = true
                                loading = true
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        YouTubeThumbnail(video.thumbnailUrl, video.title)
                        Box(
                            modifier = Modifier
                                .size(62.dp)
                                .clip(CircleShape)
                                .background(Color.White.copy(alpha = 0.92f)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(Icons.Filled.PlayArrow, contentDescription = s(R.string.play_icon_desc), tint = Color(0xFFD32F2F), modifier = Modifier.size(38.dp))
                        }
                    }
                } else if (loadFailed) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .aspectRatio(16f / 9f)
                            .clip(RoundedCornerShape(8.dp))
                            .background(MaterialTheme.colorScheme.surfaceVariant),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(8.dp)) {
                            Text(s(R.string.check_connection), color = MaterialTheme.colorScheme.secondary, fontWeight = FontWeight.Bold)
                            TextButton(onClick = {
                                loadFailed = false
                                loading = true
                                playerState = PlayerConstants.PlayerState.UNKNOWN
                                startPlayback = true
                            }) { Text("Retry") }
                        }
                    }
                } else {
                    Box {
                        if (forceWebEmbed) {
                            InAppEmbeddedYouTubePlayer(video.videoId)
                        } else {
                            YouTubeWebPlayer(
                                videoId = video.videoId,
                                onLoadFailed = {
                                    loadFailed = true
                                    loading = false
                                },
                                onLoadingChanged = { loading = it },
                                onPlayerReady = { readyPlayer ->
                                    player = readyPlayer
                                    readyPlayer.setVolume(volume.toInt())
                                },
                                onPlayerStateChanged = { state -> playerState = state },
                                onCurrentSecond = { sec -> currentSecond = sec },
                                onDuration = { total -> duration = total }
                            )
                        }
                        if (loading) {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .aspectRatio(16f / 9f)
                                    .background(Color.Black.copy(alpha = 0.28f)),
                                contentAlignment = Alignment.Center
                            ) {
                                CircularProgressIndicator(color = Color.White)
                            }
                        }
                    }
                }
                if (showControls && !forceWebEmbed) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        IconButton(onClick = {
                            player?.seekTo((currentSecond - 10f).coerceAtLeast(0f))
                        }) {
                            Icon(Icons.Filled.FastRewind, contentDescription = "Back 10s")
                        }
                        IconButton(onClick = {
                            val p = player ?: return@IconButton
                            if (playerState == PlayerConstants.PlayerState.PLAYING) p.pause() else p.play()
                        }) {
                            Icon(
                                if (playerState == PlayerConstants.PlayerState.PLAYING) Icons.Filled.Pause else Icons.Filled.PlayArrow,
                                contentDescription = "Play pause"
                            )
                        }
                        IconButton(onClick = {
                            val end = if (duration > 0f) duration else currentSecond + 10f
                            player?.seekTo((currentSecond + 10f).coerceAtMost(end))
                        }) {
                            Icon(Icons.Filled.FastForward, contentDescription = "Forward 10s")
                        }
                        IconButton(onClick = {
                            val p = player ?: return@IconButton
                            if (muted) p.unMute() else p.mute()
                            muted = !muted
                        }) {
                            Icon(if (muted) Icons.Filled.VolumeOff else Icons.Filled.VolumeUp, contentDescription = "Mute")
                        }
                        Slider(
                            value = volume,
                            onValueChange = {
                                volume = it
                                player?.setVolume(it.toInt())
                                if (it > 0 && muted) {
                                    player?.unMute()
                                    muted = false
                                }
                            },
                            valueRange = 0f..100f,
                            modifier = Modifier.weight(1f)
                        )
                        IconButton(onClick = {
                            player?.toggleFullscreen()
                            immersive = !immersive
                        }) {
                            Icon(if (immersive) Icons.Filled.FullscreenExit else Icons.Filled.Fullscreen, contentDescription = "Fullscreen")
                        }
                    }
                }
                Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    val context = LocalContext.current
                    TextButton(onClick = onDismiss) { Text(s(R.string.close)) }
                    Button(onClick = { context.openYouTubeVideo(video.videoUrl) }, shape = RoundedCornerShape(8.dp)) {
                        Text(s(R.string.open_in_youtube))
                    }
                }
            }
        }
    }
}

@Composable
private fun InAppEmbeddedYouTubePlayer(videoId: String) {
    val context = LocalContext.current
    AndroidView(
        factory = {
            WebView(context).apply {
                setBackgroundColor(android.graphics.Color.BLACK)
                setLayerType(View.LAYER_TYPE_HARDWARE, null)
                settings.javaScriptEnabled = true
                settings.domStorageEnabled = true
                settings.loadsImagesAutomatically = true
                settings.useWideViewPort = true
                settings.loadWithOverviewMode = true
                settings.builtInZoomControls = false
                settings.cacheMode = WebSettings.LOAD_DEFAULT
                settings.mediaPlaybackRequiresUserGesture = false
                settings.mixedContentMode = WebSettings.MIXED_CONTENT_COMPATIBILITY_MODE
                CookieManager.getInstance().setAcceptCookie(true)
                CookieManager.getInstance().setAcceptThirdPartyCookies(this, true)
                webChromeClient = WebChromeClient()
                webViewClient = object : WebViewClient() {
                    override fun onPageFinished(view: WebView?, url: String?) {
                        super.onPageFinished(view, url)
                        view?.visibility = View.VISIBLE
                    }
                }
                visibility = View.VISIBLE
                loadUrl("https://www.youtube.com/embed/$videoId?autoplay=1&playsinline=1&controls=1&fs=1&rel=0&modestbranding=1")
            }
        },
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(16f / 9f)
            .clip(RoundedCornerShape(8.dp))
    )
}

@Composable
private fun YouTubeWebPlayer(
    videoId: String,
    onLoadFailed: () -> Unit,
    onLoadingChanged: (Boolean) -> Unit,
    onPlayerReady: (YouTubePlayer) -> Unit,
    onPlayerStateChanged: (PlayerConstants.PlayerState) -> Unit,
    onCurrentSecond: (Float) -> Unit,
    onDuration: (Float) -> Unit
) {
    val context = LocalContext.current
    AndroidView(
        factory = {
            WebView(context).apply {
                setBackgroundColor(android.graphics.Color.BLACK)
                settings.javaScriptEnabled = true
                settings.domStorageEnabled = true
                settings.loadsImagesAutomatically = true
                settings.useWideViewPort = true
                settings.loadWithOverviewMode = true
                settings.mediaPlaybackRequiresUserGesture = false
                settings.cacheMode = WebSettings.LOAD_DEFAULT
                settings.mixedContentMode = WebSettings.MIXED_CONTENT_COMPATIBILITY_MODE
                CookieManager.getInstance().setAcceptCookie(true)
                CookieManager.getInstance().setAcceptThirdPartyCookies(this, true)
                webChromeClient = WebChromeClient()
                webViewClient = object : WebViewClient() {
                    override fun onPageStarted(view: WebView?, url: String?, favicon: android.graphics.Bitmap?) {
                        onLoadingChanged(true)
                    }
                    override fun onPageFinished(view: WebView?, url: String?) {
                        onLoadingChanged(false)
                    }
                    override fun onReceivedHttpError(view: WebView?, request: android.webkit.WebResourceRequest?, errorResponse: android.webkit.WebResourceResponse?) {
                        if (request?.isForMainFrame == true) {
                            onLoadingChanged(false)
                            onLoadFailed()
                        }
                    }
                }
                loadUrl("https://m.youtube.com/watch?v=$videoId")
            }
        },
        update = { },
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(16f / 9f)
            .clip(RoundedCornerShape(8.dp))
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CatalogScreen(copy: Copy, language: Language, onAddToCart: (Toy) -> Unit, onBuyNow: (Toy) -> Unit) {
    var selected by rememberSaveable { mutableStateOf("All") }
    val categories = listOf("All") + HeritageRepository.allToys.map { it.category }.distinct()
    val filtered = if (selected == "All") HeritageRepository.allToys else HeritageRepository.allToys.filter { it.category == selected }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(20.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        item { Text(copy.products, fontSize = 28.sp, fontWeight = FontWeight.Black) }
        item {
            LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                items(categories) { category ->
                    FilterChip(selected = selected == category, onClick = { selected = category }, label = { Text(category) })
                }
            }
        }
        items(filtered) { toy -> ProductCard(toy, copy, language, onAddToCart, onBuyNow) }
    }
}

@Composable
private fun ProductCard(
    toy: Toy,
    copy: Copy,
    language: Language,
    onAddToCart: ((Toy) -> Unit)? = null,
    onBuyNow: ((Toy) -> Unit)? = null
) {
    val artisan = HeritageRepository.artisans.first { it.id == toy.artisanId }
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(3.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Row(Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
            Image(
                painter = painterResource(toy.imageRes),
                contentDescription = toy.name,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(96.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(MaterialTheme.colorScheme.surfaceVariant)
            )
            Spacer(Modifier.width(14.dp))
            Column(Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(7.dp)) {
                Text(if (language == Language.Kannada) toy.kannadaName else toy.name, fontWeight = FontWeight.Bold, fontSize = 18.sp)
                Text("Toy ID ${toy.id}", color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.SemiBold, fontSize = 12.sp)
                Text(toy.story, color = MaterialTheme.colorScheme.onSurface, maxLines = 2, overflow = TextOverflow.Ellipsis)
                Text("${copy.certified}: ${artisan.name}", color = MaterialTheme.colorScheme.outline, maxLines = 1, overflow = TextOverflow.Ellipsis)
                Text("${copy.vegetableDyes}: ${toy.dye}", color = MaterialTheme.colorScheme.outline, maxLines = 2, overflow = TextOverflow.Ellipsis)
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(toy.price, fontWeight = FontWeight.Bold)
                    Spacer(Modifier.weight(1f))
                    Icon(Icons.Filled.Badge, contentDescription = null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(18.dp))
                }
                onAddToCart?.let { add ->
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.fillMaxWidth()) {
                        Button(
                            onClick = { add(toy) },
                            shape = RoundedCornerShape(8.dp),
                            modifier = Modifier.weight(1f)
                        ) {
                            Icon(Icons.Filled.ShoppingCart, contentDescription = null, modifier = Modifier.size(18.dp))
                            Spacer(Modifier.width(6.dp))
                    Text(s(R.string.action_add))
                        }
                        Button(
                            onClick = { onBuyNow?.invoke(toy) ?: add(toy) },
                            shape = RoundedCornerShape(8.dp),
                            modifier = Modifier.weight(1f),
                            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary)
                        ) {
                            Text(s(R.string.action_buy_now))
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun MakersScreen(copy: Copy, language: Language) {
    var selectedArtisan by remember { mutableStateOf<Artisan?>(HeritageRepository.artisans.first()) }
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(20.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        item {
            Text(copy.makers, fontSize = 28.sp, fontWeight = FontWeight.Black)
            Text(copy.tapMarker, color = MaterialTheme.colorScheme.outline)
        }
        item {
            ArtisanGoogleMap(
                artisans = HeritageRepository.artisans,
                selectedArtisan = selectedArtisan,
                onMarkerClick = { selectedArtisan = it }
            )
        }
        selectedArtisan?.let { artisan ->
            item { ArtisanCard(artisan, copy, language) }
        }
        items(HeritageRepository.artisans) { artisan ->
            ArtisanCard(artisan, copy, language, compact = true)
        }
    }
}

@Composable
private fun ArtisanGoogleMap(artisans: List<Artisan>, selectedArtisan: Artisan?, onMarkerClick: (Artisan) -> Unit) {
    val context = LocalContext.current
    val center = remember(artisans) { GeoPoint(artisans.map { it.lat }.average(), artisans.map { it.lng }.average()) }

    Card(
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(6.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp)
                    .background(Color(0xFFE7F3E4))
            ) {
                AndroidView(
                    factory = {
                        Configuration.getInstance().userAgentValue = context.packageName
                        MapView(context).apply {
                            setTileSource(TileSourceFactory.MAPNIK)
                            setMultiTouchControls(true)
                            setBuiltInZoomControls(true)
                            zoomController.setVisibility(CustomZoomButtonsController.Visibility.SHOW_AND_FADEOUT)
                            minZoomLevel = 10.0
                            maxZoomLevel = 19.0
                            controller.setZoom(14.5)
                            controller.setCenter(center)
                        }
                    },
                    update = { mapView ->
                        val existingMarkers = mapView.overlays.filterIsInstance<Marker>().toList()
                        existingMarkers.forEach { mapView.overlays.remove(it) }
                        artisans.forEach { artisan ->
                            val marker = Marker(mapView).apply {
                                position = GeoPoint(artisan.lat, artisan.lng)
                                title = artisan.name
                                subDescription = artisan.studio
                                setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
                                icon = ContextCompat.getDrawable(context, org.osmdroid.library.R.drawable.marker_default)?.mutate()?.also { drawable ->
                                    val tint = if (artisan == selectedArtisan) android.graphics.Color.parseColor("#D32F2F") else android.graphics.Color.parseColor("#388E3C")
                                    DrawableCompat.setTint(drawable, tint)
                                }
                                setOnMarkerClickListener { _, _ ->
                                    onMarkerClick(artisan)
                                    showInfoWindow()
                                    true
                                }
                            }
                            mapView.overlays.add(marker)
                            if (artisan == selectedArtisan) {
                                marker.showInfoWindow()
                            }
                        }
                        selectedArtisan?.let { mapView.controller.animateTo(GeoPoint(it.lat, it.lng)) }
                        mapView.invalidate()
                    },
                    modifier = Modifier.fillMaxSize()
                )
                Column(
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .padding(16.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(Color.White.copy(alpha = 0.92f))
                        .padding(12.dp)
                ) {
                    Text(s(R.string.maker_map_preview), fontWeight = FontWeight.Black, fontSize = 17.sp)
                    Text(s(R.string.maker_map_count), color = MaterialTheme.colorScheme.outline)
                }
            }
            Column(
                modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                artisans.forEach { artisan ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(8.dp))
                            .clickable { onMarkerClick(artisan) }
                            .background(if (artisan == selectedArtisan) Color(0xFFFFECB3) else Color.Transparent)
                            .padding(10.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(Icons.Filled.LocationOn, contentDescription = null, tint = if (artisan == selectedArtisan) Color(0xFFD32F2F) else Color(0xFF388E3C))
                        Spacer(Modifier.width(8.dp))
                        Column(Modifier.weight(1f)) {
                            Text(artisan.name, fontWeight = FontWeight.Bold)
                            Text(artisan.studio, color = MaterialTheme.colorScheme.outline, maxLines = 1, overflow = TextOverflow.Ellipsis)
                        }
                        Text("${artisan.lat}, ${artisan.lng}", color = MaterialTheme.colorScheme.primary, fontSize = 11.sp)
                    }
                }
            }
        }
    }
}

@Composable
private fun CartScreen(
    cartItems: List<CartItem>,
    onQuantityChange: (CartItem, Int) -> Unit,
    onRemove: (CartItem) -> Unit,
    onCheckout: (CheckoutDetails, (String) -> Unit) -> Unit
) {
    var orderMessage by rememberSaveable { mutableStateOf<String?>(null) }
    var address by rememberSaveable { mutableStateOf("") }
    var phone by rememberSaveable { mutableStateOf("") }
    var paymentMethod by rememberSaveable { mutableStateOf("Cash on Delivery") }
    val total = cartItems.sumOf { it.totalPrice() }
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(20.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        item {
            Text(s(R.string.cart_title), fontSize = 28.sp, fontWeight = FontWeight.Black)
            Text(s(R.string.cart_subtitle), color = MaterialTheme.colorScheme.outline)
        }
        if (cartItems.isEmpty()) {
            item {
                Card(shape = RoundedCornerShape(8.dp), colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)) {
                    Text(s(R.string.cart_empty), modifier = Modifier.padding(18.dp), color = MaterialTheme.colorScheme.outline)
                }
            }
        } else {
            items(cartItems) { item ->
                CartItemCard(
                    item = item,
                    onDecrease = { onQuantityChange(item, (item.quantity - 1).coerceAtLeast(1)) },
                    onIncrease = { onQuantityChange(item, item.quantity + 1) },
                    onRemove = { onRemove(item) }
                )
            }
            item {
                ElevatedCard(shape = RoundedCornerShape(8.dp), colors = CardDefaults.elevatedCardColors(containerColor = MaterialTheme.colorScheme.surface)) {
                    Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                        Text(s(R.string.order_summary), fontWeight = FontWeight.Black, fontSize = 20.sp)
                        Text(s(R.string.total_rs_fmt, total), fontWeight = FontWeight.Black, color = MaterialTheme.colorScheme.primary)
                        OutlinedTextField(
                            value = address,
                            onValueChange = { address = it },
                            label = { Text(s(R.string.delivery_address)) },
                            modifier = Modifier.fillMaxWidth()
                        )
                        OutlinedTextField(
                            value = phone,
                            onValueChange = { phone = it.filter(Char::isDigit).take(10) },
                            label = { Text(s(R.string.phone)) },
                            singleLine = true,
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                            modifier = Modifier.fillMaxWidth()
                        )
                        LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            items(listOf("Cash on Delivery", "UPI", "Card")) { method ->
                                FilterChip(
                                    selected = paymentMethod == method,
                                    onClick = { paymentMethod = method },
                                    label = { Text(method) }
                                )
                            }
                        }
                    }
                }
            }
            item {
                orderMessage?.let {
                    Text(it, color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.Bold)
                    Spacer(Modifier.height(8.dp))
                }
                Button(
                    onClick = {
                        val error = validateCheckout(address, phone)
                        if (error != null) {
                            orderMessage = error
                        } else {
                            onCheckout(CheckoutDetails(address.trim(), phone.trim(), paymentMethod)) { message ->
                                orderMessage = message
                                if (message.startsWith("Order placed")) {
                                    address = ""
                                    phone = ""
                                }
                            }
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary)
                ) {
                    Icon(Icons.Filled.CheckCircle, contentDescription = null)
                    Spacer(Modifier.width(8.dp))
                    Text(s(R.string.checkout))
                }
            }
        }
    }
}

@Composable
private fun CartItemCard(item: CartItem, onDecrease: () -> Unit, onIncrease: () -> Unit, onRemove: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(4.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Column(Modifier.weight(1f)) {
                    Text(item.toyName, fontWeight = FontWeight.Bold, fontSize = 18.sp)
                    Text(s(R.string.toy_id_fmt, item.toyId), color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.SemiBold)
                    Text(item.price, color = MaterialTheme.colorScheme.outline)
                    Text(s(R.string.item_total_rs_fmt, item.totalPrice()), fontWeight = FontWeight.SemiBold)
                }
                IconButton(onClick = onRemove) {
                    Icon(Icons.Filled.Delete, contentDescription = s(R.string.remove), tint = MaterialTheme.colorScheme.secondary)
                }
            }
            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                Button(onClick = onDecrease, shape = RoundedCornerShape(8.dp)) { Text("-") }
                Text(s(R.string.qty_fmt, item.quantity), fontWeight = FontWeight.Bold)
                Button(onClick = onIncrease, shape = RoundedCornerShape(8.dp)) { Text("+") }
            }
        }
    }
}

@Composable
private fun ProfileScreen(user: AppUser, orders: List<OrderItem>, onLogout: () -> Unit) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(20.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        item {
            Text(s(R.string.profile_title), fontSize = 28.sp, fontWeight = FontWeight.Black)
        }
        item {
            ElevatedCard(shape = RoundedCornerShape(8.dp), colors = CardDefaults.elevatedCardColors(containerColor = MaterialTheme.colorScheme.surface)) {
                Column(Modifier.padding(18.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    Icon(Icons.Filled.Person, contentDescription = null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(54.dp))
                    Text(user.name, fontWeight = FontWeight.Black, fontSize = 22.sp)
                    Text(user.email, color = MaterialTheme.colorScheme.outline)
                    Text(s(R.string.firebase_account), color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.SemiBold)
                    Text(
                        "Lord CM",
                        modifier = Modifier.fillMaxWidth(),
                        color = MaterialTheme.colorScheme.outline.copy(alpha = 0.45f),
                        fontSize = 10.sp
                    )
                    Button(
                        onClick = onLogout,
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(8.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary)
                    ) {
                        Icon(Icons.AutoMirrored.Filled.Logout, contentDescription = null)
                        Spacer(Modifier.width(8.dp))
                        Text(s(R.string.logout))
                    }
                }
            }
        }
        item { Text(s(R.string.my_orders), fontSize = 22.sp, fontWeight = FontWeight.Black) }
        if (orders.isEmpty()) {
            item {
                Card(shape = RoundedCornerShape(8.dp), colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)) {
                    Text(s(R.string.no_orders_yet), modifier = Modifier.padding(18.dp), color = MaterialTheme.colorScheme.outline)
                }
            }
        } else {
            items(orders) { order -> OrderCard(order) }
        }
    }
}

@Composable
private fun OrderCard(order: OrderItem) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(4.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
            Text(order.orderId, fontWeight = FontWeight.Black, fontSize = 18.sp)
            Text(s(R.string.total_rs_fmt, order.total), color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.Bold)
            DeliveryStatus(status = order.status)
            Text(s(R.string.delivery_agent_fmt, order.agentName), fontWeight = FontWeight.SemiBold)
            Text(s(R.string.phone_fmt, order.agentPhone), color = MaterialTheme.colorScheme.outline)
        }
    }
}

@Composable
private fun DeliveryStatus(status: String) {
    val steps = listOf("Placed", "Packed", "Shipped", "Out for Delivery", "Delivered")
    val activeIndex = steps.indexOf(status).coerceAtLeast(0)
    Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
        steps.forEachIndexed { index, step ->
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Filled.CheckCircle,
                    contentDescription = null,
                    tint = if (index <= activeIndex) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outline,
                    modifier = Modifier.size(18.dp)
                )
                Spacer(Modifier.width(8.dp))
                Text(step, color = if (index <= activeIndex) MaterialTheme.colorScheme.onSurface else MaterialTheme.colorScheme.outline)
            }
        }
    }
}

@Composable
private fun CollectionScreen(copy: Copy, items: List<CollectionToy>, onAdd: (CollectionToy) -> Unit) {
    var toyId by rememberSaveable { mutableStateOf("") }
    var description by rememberSaveable { mutableStateOf("") }
    var price by rememberSaveable { mutableStateOf("") }
    var imageUri by rememberSaveable { mutableStateOf<String?>(null) }
    var error by rememberSaveable { mutableStateOf<String?>(null) }
    val context = LocalContext.current
    val imagePicker = rememberLauncherForActivityResult(ActivityResultContracts.OpenDocument()) { uri ->
        uri?.let {
            context.contentResolver.takePersistableUriPermission(it, Intent.FLAG_GRANT_READ_URI_PERMISSION)
        }
        imageUri = uri?.toString()
        error = null
    }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(20.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        item {
            Text(copy.myCollection, fontSize = 28.sp, fontWeight = FontWeight.Black)
            Text(s(R.string.collection_help), color = MaterialTheme.colorScheme.outline)
        }
        item {
            ElevatedCard(shape = RoundedCornerShape(8.dp), colors = CardDefaults.elevatedCardColors(containerColor = MaterialTheme.colorScheme.surface)) {
                Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    Text(copy.addCollection, fontWeight = FontWeight.Bold, fontSize = 20.sp)
                    OutlinedTextField(
                        value = toyId,
                        onValueChange = { toyId = it.filter(Char::isDigit).take(6) },
                        label = { Text(copy.searchCode) },
                        leadingIcon = { Icon(Icons.Filled.Verified, contentDescription = null) },
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword),
                        modifier = Modifier.fillMaxWidth()
                    )
                    OutlinedTextField(
                        value = description,
                        onValueChange = { description = it },
                        label = { Text(copy.toyDescription) },
                        modifier = Modifier.fillMaxWidth()
                    )
                    OutlinedTextField(
                        value = price,
                        onValueChange = { price = it },
                        label = { Text(copy.price) },
                        leadingIcon = { Text(s(R.string.rs_short)) },
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                        modifier = Modifier.fillMaxWidth()
                    )
                    Button(
                        onClick = { imagePicker.launch(arrayOf("image/jpeg")) },
                        shape = RoundedCornerShape(8.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.tertiary, contentColor = Color(0xFF241B12))
                    ) {
                        Icon(Icons.Filled.PhotoCamera, contentDescription = null)
                        Spacer(Modifier.width(8.dp))
                        Text(if (imageUri == null) copy.uploadJpg else s(R.string.jpg_selected))
                    }
                    imageUri?.let {
                        UriImage(uri = it, modifier = Modifier.fillMaxWidth().height(150.dp).clip(RoundedCornerShape(8.dp)))
                    }
                    error?.let { Text(it, color = MaterialTheme.colorScheme.secondary, fontWeight = FontWeight.SemiBold) }
                    Button(
                        onClick = {
                            val uri = imageUri
                            if (toyId.length != 6 || description.isBlank() || price.isBlank() || uri == null) {
                                error = copy.requiredFields
                            } else {
                                onAdd(CollectionToy(toyId, description.trim(), price.trim(), uri))
                                toyId = ""
                                description = ""
                                price = ""
                                imageUri = null
                                error = null
                            }
                        },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Icon(Icons.Filled.CollectionsBookmark, contentDescription = null)
                        Spacer(Modifier.width(8.dp))
                        Text(copy.saveToy)
                    }
                }
            }
        }
        if (items.isEmpty()) {
            item {
                Card(shape = RoundedCornerShape(8.dp), colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)) {
                    Text(copy.collectionEmpty, modifier = Modifier.padding(18.dp), color = MaterialTheme.colorScheme.outline)
                }
            }
        } else {
            items(items) { item ->
                CollectionToyCard(item)
            }
        }
    }
}

@Composable
private fun CollectionToyCard(item: CollectionToy) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(4.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Row(Modifier.padding(14.dp), verticalAlignment = Alignment.CenterVertically) {
            UriImage(uri = item.imageUri, modifier = Modifier.size(96.dp).clip(RoundedCornerShape(8.dp)))
            Spacer(Modifier.width(14.dp))
            Column(Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(6.dp)) {
                Text(s(R.string.toy_id_fmt, item.toyId), fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
                Text(item.description, maxLines = 2, overflow = TextOverflow.Ellipsis)
                Text(item.price, fontWeight = FontWeight.Black, color = MaterialTheme.colorScheme.secondary)
            }
        }
    }
}

@Composable
private fun UriImage(uri: String, modifier: Modifier = Modifier) {
    val context = LocalContext.current
    AndroidView(
        factory = {
            ImageView(context).apply {
                scaleType = ImageView.ScaleType.CENTER_CROP
                setImageURI(Uri.parse(uri))
            }
        },
        update = { it.setImageURI(Uri.parse(uri)) },
        modifier = modifier.background(MaterialTheme.colorScheme.surfaceVariant)
    )
}

@Composable
private fun ArtisanCard(artisan: Artisan, copy: Copy, language: Language, compact: Boolean = false) {
    val context = LocalContext.current
    Card(shape = RoundedCornerShape(8.dp), elevation = CardDefaults.cardElevation(if (compact) 2.dp else 5.dp), colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)) {
        Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(
                    painter = painterResource(artisan.imageRes),
                    contentDescription = artisan.name,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(if (compact) 50.dp else 64.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.surfaceVariant)
                )
                Spacer(Modifier.width(12.dp))
                Column(Modifier.weight(1f)) {
                    Text(if (language == Language.Kannada) artisan.kannadaName else artisan.name, fontWeight = FontWeight.Bold, fontSize = if (compact) 17.sp else 19.sp)
                    Text(artisan.studio, color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.SemiBold)
                    Text("ID ${artisan.id}", color = MaterialTheme.colorScheme.outline, fontSize = 12.sp)
                }
                Icon(Icons.Filled.Verified, contentDescription = copy.certified, tint = MaterialTheme.colorScheme.primary)
            }
            Text("${artisan.years} years | ${artisan.specialty}", fontWeight = FontWeight.SemiBold)
            if (!compact) Text(artisan.bio, color = MaterialTheme.colorScheme.outline, lineHeight = 19.sp)
            TextButton(onClick = { context.openMaps(artisan.lat, artisan.lng, artisan.studio) }) {
                Icon(Icons.Filled.Navigation, contentDescription = null, modifier = Modifier.size(18.dp))
                Spacer(Modifier.width(8.dp))
                Text(copy.navigate)
            }
        }
    }
}

@Composable
private fun ToyMark(modifier: Modifier = Modifier) {
    Canvas(modifier) {
        drawCircle(Color(0xFFFFC107))
        drawCircle(Color(0xFFD32F2F), radius = size.minDimension * 0.34f)
        drawCircle(Color(0xFF388E3C), radius = size.minDimension * 0.13f)
    }
}

private fun android.content.Context.openMaps(lat: Double, lng: Double, label: String) {
    val encodedLabel = Uri.encode(label)
    val uri = Uri.parse("geo:$lat,$lng?q=$lat,$lng($encodedLabel)")
    val intent = Intent(Intent.ACTION_VIEW, uri).apply { setPackage("com.google.android.apps.maps") }
    val fallback = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.com/maps/search/?api=1&query=$lat,$lng"))
    startActivity(intent.takeIf { it.resolveActivity(packageManager) != null } ?: fallback)
}

private fun android.content.Context.openYouTubeVideo(videoUrl: String) {
    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(videoUrl))
    startActivity(intent)
}

private fun extractYouTubeVideoId(videoUrl: String): String {
    val uri = Uri.parse(videoUrl)
    return when {
        uri.host?.contains("youtu.be") == true -> uri.lastPathSegment.orEmpty()
        uri.pathSegments.firstOrNull() == "shorts" -> uri.pathSegments.getOrNull(1).orEmpty()
        else -> uri.getQueryParameter("v").orEmpty()
    }
}

private fun validateLogin(email: String, password: String): String? = when {
    email.isBlank() || password.isBlank() -> "Email and password are required."
    !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches() -> "Enter a valid email address."
    password.length < 6 -> "Password must be at least 6 characters."
    else -> null
}

private fun validateSignup(name: String, email: String, password: String, confirmPassword: String): String? = when {
    name.trim().length < 3 -> "Name must be at least 3 characters."
    email.isBlank() || password.isBlank() || confirmPassword.isBlank() -> "All fields are required."
    !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches() -> "Enter a valid email address."
    password.length < 6 -> "Password must be at least 6 characters."
    password != confirmPassword -> "Passwords do not match."
    else -> null
}

private fun validateCheckout(address: String, phone: String): String? = when {
    address.trim().length < 8 -> "Enter a complete delivery address."
    phone.trim().length != 10 -> "Enter a valid 10-digit phone number."
    else -> null
}

private fun CartItem.unitPrice(): Int = price.filter(Char::isDigit).toIntOrNull() ?: 0

private fun CartItem.totalPrice(): Int = unitPrice() * quantity

private fun FirebaseUser.toAppUser() = AppUser(
    uid = uid,
    name = email?.substringBefore("@").orEmpty().ifBlank { "User" },
    email = email.orEmpty(),
    firebaseUser = this
)

private fun Exception.toFriendlyFirebaseMessage(fallback: String): String {
    val text = "${message.orEmpty()} ${localizedMessage.orEmpty()}"
    if (text.contains("CONFIGURATION_NOT_FOUND", ignoreCase = true)) {
        return "Firebase Authentication is not enabled for project $FIREBASE_PROJECT_ID. Open that same Firebase project, then enable Authentication > Email/Password."
    }
    if (text.contains("badly formatted", ignoreCase = true) || text.contains("invalid email", ignoreCase = true)) {
        return "Please enter valid email."
    }
    if (text.contains("no user record", ignoreCase = true) || text.contains("user-not-found", ignoreCase = true)) {
        return "User not found. Please sign up first."
    }
    if (text.contains("password is invalid", ignoreCase = true) || text.contains("wrong-password", ignoreCase = true)) {
        return "Password is incorrect."
    }
    if (
        text.contains("INVALID_LOGIN_CREDENTIALS", ignoreCase = true) ||
        text.contains("supplied auth credential", ignoreCase = true) ||
        text.contains("credential is incorrect", ignoreCase = true)
    ) {
        return "Incorrect email or password."
    }
    if (text.contains("email address is already in use", ignoreCase = true)) {
        return "This email is already registered. Please login."
    }
    return localizedMessage ?: fallback
}

private fun FirebaseFirestore.loadUserProfile(
    user: FirebaseUser,
    onSuccess: (AppUser) -> Unit,
    onFailure: (Exception?) -> Unit
) {
    val uid = user.uid
    collection("users").document(uid).get()
        .addOnSuccessListener { document ->
            if (!document.exists()) {
                onFailure(null)
                return@addOnSuccessListener
            }
            onSuccess(
                AppUser(
                    uid = uid,
                    name = document.getString("name") ?: user.email?.substringBefore("@").orEmpty().ifBlank { "User" },
                    email = document.getString("email") ?: user.email.orEmpty(),
                    firebaseUser = user
                )
            )
        }
        .addOnFailureListener { onFailure(it) }
}

private fun FirebaseFirestore.addToyToCart(uid: String, toy: Toy) {
    val document = collection("cart").document(uid).collection("items").document(toy.id)
    runTransaction { transaction ->
        val snapshot = transaction.get(document)
        val currentQuantity = snapshot.getLong("quantity") ?: 0L
        transaction.set(
            document,
            mapOf(
                "toyName" to toy.name,
                "price" to toy.price,
                "description" to toy.story,
                "unitPrice" to (toy.price.filter(Char::isDigit).toIntOrNull() ?: 0),
                "quantity" to currentQuantity + 1,
                "updatedAt" to System.currentTimeMillis()
            ),
            SetOptions.merge()
        )
    }
}

private fun FirebaseFirestore.updateCartQuantity(uid: String, toyId: String, quantity: Int) {
    collection("cart").document(uid).collection("items").document(toyId)
        .set(mapOf("quantity" to quantity.coerceAtLeast(1), "updatedAt" to System.currentTimeMillis()), SetOptions.merge())
}

private fun FirebaseFirestore.removeCartItem(uid: String, toyId: String) {
    collection("cart").document(uid).collection("items").document(toyId).delete()
}

private fun FirebaseFirestore.placeOrder(uid: String, cartItems: List<CartItem>, details: CheckoutDetails, onResult: (String) -> Unit) {
    if (cartItems.isEmpty()) return
    val orderId = "ORD-${System.currentTimeMillis()}"
    val order = mapOf(
        "orderId" to orderId,
        "items" to cartItems.map {
            mapOf(
                "toyId" to it.toyId,
                "toyName" to it.toyName,
                "price" to it.price,
                "quantity" to it.quantity,
                "lineTotal" to it.totalPrice()
            )
        },
        "address" to details.address,
        "phone" to details.phone,
        "paymentMethod" to details.paymentMethod,
        "status" to "Placed",
        "total" to cartItems.sumOf { it.totalPrice() },
        "agentName" to "Ravi Kumar",
        "agentPhone" to "98765 43210",
        "createdAt" to System.currentTimeMillis()
    )
    val batch = batch()
    val orderRef = collection("orders").document(uid).collection("items").document(orderId)
    batch.set(orderRef, order)
    cartItems.forEach { item ->
        batch.delete(collection("cart").document(uid).collection("items").document(item.toyId))
    }
    batch.commit()
        .addOnSuccessListener { onResult("Order placed successfully.") }
        .addOnFailureListener { onResult(it.localizedMessage ?: "Order failed. Please try again.") }
}

private fun android.content.Context.loadCollectionToys(): List<CollectionToy> {
    val stored = getSharedPreferences("collection", android.content.Context.MODE_PRIVATE).getString("items", "[]") ?: "[]"
    return runCatching {
        val array = JSONArray(stored)
        List(array.length()) { index ->
            val item = array.getJSONObject(index)
            CollectionToy(
                toyId = item.getString("toyId"),
                description = item.getString("description"),
                price = item.getString("price"),
                imageUri = item.getString("imageUri")
            )
        }
    }.getOrDefault(emptyList())
}

private fun android.content.Context.saveCollectionToys(items: List<CollectionToy>) {
    val array = JSONArray()
    items.forEach { item ->
        array.put(
            JSONObject()
                .put("toyId", item.toyId)
                .put("description", item.description)
                .put("price", item.price)
                .put("imageUri", item.imageUri)
        )
    }
    getSharedPreferences("collection", android.content.Context.MODE_PRIVATE)
        .edit()
        .putString("items", array.toString())
        .apply()
}

