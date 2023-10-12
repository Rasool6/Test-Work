package com.example.testingwork_app

import android.app.DownloadManager
import android.content.Context
import android.content.pm.PackageManager
import android.net.Uri
import android.os.AsyncTask
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.widget.Toast
import com.example.testingwork_app.databinding.ActivityMainBinding
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element
import java.lang.ref.WeakReference

class MainActivity : AppCompatActivity() {
    var bindingMain:ActivityMainBinding?=null
    val binding get() = bindingMain!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindingMain=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        startDownloading()
       /* getPermissions()
//In my case I used a button
        binding.textView.setOnClickListener {
            val url = binding.editTextText.text.toString()
            val task = MyAsyncTask(this)
            task.execute(url)
        }
*/


        val videoUrl = "https://www.facebook.com/watch/?extid=CL-UNK-UNK-UNK-AN_GK0T-GK1C&mibextid=2Rb1fB&v=651157193637600"
//        val videoUrl = "https://www.facebook.com/watch/?extid=CL-UNK-UNK-UNK-AN_GK0T-GK1C&mibextid=2Rb1fB&v=651157193637600"

        try {
            val doc: Document = Jsoup.connect(videoUrl).get()

            // Extract video title
            val videoTitle = doc.select("meta[property=og:title]").attr("content")

            // Extract video description
            val videoDescription = doc.select("meta[property=og:description]").attr("content")

            // Extract video thumbnail URL
            val videoThumbnailUrl = doc.select("meta[property=og:image]").attr("content")

            Log.d("varMsg", "Video Title: $videoTitle")
            Log.d("varMsg", "Video Description: $videoDescription")
            Log.d("varMsg", "Video Thumbnail URL: $videoThumbnailUrl")

//            println("Video Title: $videoTitle")
//            println("Video Description: $videoDescription")
//            println("Video Thumbnail URL: $videoThumbnailUrl")

        } catch (e: Exception) {
            println("An error occurred: ${e.message}")
        }
    }

    private fun getPermissions() {
        if (checkSelfPermission(
                android.Manifest.permission.READ_MEDIA_VIDEO) == PackageManager.PERMISSION_DENIED
        ){
            requestPermissions(arrayOf(android.Manifest.permission.READ_MEDIA_VIDEO), 1)
        }
        else{
            //do something no one cares
        }

    }
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when(requestCode){
            1 -> {
                if (grantResults.isNotEmpty() && grantResults[0] ==
                    PackageManager.PERMISSION_GRANTED){
                    val url = binding.editTextText.text.toString()
                    val task = MyAsyncTask(this)
                    task.execute(url)
                }
                else{
                    Toast.makeText(this,"Allow all the permissions to continue",Toast.LENGTH_LONG).show()
                    getPermissions()
                }
            }
        }
    }
    private fun startDownloading() {


        try {


        val request = DownloadManager.Request(Uri.parse("https://scontent.xx.fbcdn.net/v/t39.25447-2/363325220_831757741908695_2726139664761130680_n.mp4?_nc_cat=104&vs=52a7838eeb96110e&_nc_vs=HBksFQAYJEdDVG5weFhYNHI2d2V2UUNBTGlPTU5hVU1kVWxibWRqQUFBRhUAAsgBABUAGCRHSExYT2diQmdrbTNSLTRCQVBwMTZWY1Zzc28zYnY0R0FBQUYVAgLIAQBLB4gScHJvZ3Jlc3NpdmVfcmVjaXBlATENc3Vic2FtcGxlX2ZwcwAQdm1hZl9lbmFibGVfbnN1YgAgbWVhc3VyZV9vcmlnaW5hbF9yZXNvbHV0aW9uX3NzaW0AKGNvbXB1dGVfc3NpbV9vbmx5X2F0X29yaWdpbmFsX3Jlc29sdXRpb24AHXVzZV9sYW5jem9zX2Zvcl92cW1fdXBzY2FsaW5nABFkaXNhYmxlX3Bvc3RfcHZxcwAVACUAHIwXQAAAAAAAAAAREQAAACbk25akjZm9ChUCKAJDMxgLdnRzX3ByZXZpZXccF0B9gAAAAAAAGClkYXNoX2k0bGl0ZWJhc2ljXzVzZWNnb3BfaHEyX2ZyYWdfMl92aWRlbxIAGBh2aWRlb3MudnRzLmNhbGxiYWNrLnByb2Q4ElZJREVPX1ZJRVdfUkVRVUVTVBsKiBVvZW1fdGFyZ2V0X2VuY29kZV90YWcGb2VwX2hkE29lbV9yZXF1ZXN0X3RpbWVfbXMBMAxvZW1fY2ZnX3J1bGUHdW5tdXRlZBNvZW1fcm9pX3JlYWNoX2NvdW50BzE3NTYwODQRb2VtX2lzX2V4cGVyaW1lbnQADG9lbV92aWRlb19pZBAyOTQ5MzIxNDc1MTkwMTI5Em9lbV92aWRlb19hc3NldF9pZBAyOTQ5MzIxNDY4NTIzNDYzFW9lbV92aWRlb19yZXNvdXJjZV9pZBAyOTQ5MzIxNDY1MTkwMTMwHG9lbV9zb3VyY2VfdmlkZW9fZW5jb2RpbmdfaWQQMTkzNDYwMjk4MDIyODUwMg52dHNfcmVxdWVzdF9pZAAlAhwAJb4BGweIAXMEMzQ2OQJjZAoyMDIwLTA2LTIwA3JjYgcxNzU2MDAwA2FwcAVWaWRlbwJjdBlDT05UQUlORURfUE9TVF9BVFRBQ0hNRU5UE29yaWdpbmFsX2R1cmF0aW9uX3MHNDcyLjEyOAJ0cxVwcm9ncmVzc2l2ZV9lbmNvZGluZ3MA&ccb=1-7&_nc_sid=894f7d&efg=eyJ2ZW5jb2RlX3RhZyI6Im9lcF9oZCJ9&_nc_ohc=uh8lfPEGKZIAX_dDHpA&_nc_ht=video.fisb13-1.fna&oh=00_AfBBamdv4gdK2kARoGu-ILXW3oko6NSaDVvaO1uDGVMXYw&oe=64ECACCB&_nc_rid=409229423929388"))
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI or DownloadManager.Request.NETWORK_MOBILE)
        request.setTitle("Download")
        request.setDescription("The file is downloading ..")
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "${System.currentTimeMillis()}.mp4")

        val manager = getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
        manager.enqueue(request)

        }catch (e:Exception){
            Log.d("varMsg", "startDownloading: ${e.message}")
        }
    }

    companion object {
        class MyAsyncTask internal constructor(context: MainActivity) : AsyncTask<String, String, String>() {

            private var resp: String? = null
            private val activityReference: WeakReference<MainActivity> = WeakReference(context)

            override fun onPreExecute() {
                val activity = activityReference.get()
                Toast.makeText(activity, "Download Started", Toast.LENGTH_SHORT).show()
            }


            override fun doInBackground(vararg params: String?): String? {
                try {
                    //Get the video link and store it in url
                    val url = params[0]
                    //Document to get the full HTML of the video link
                    val document: Document = Jsoup.connect(url).get()
                    Log.d("varMsg",document.body().toString())
                    //select the first video tag since it contains             the video  src
                    val img: Element = document.select("video").first()

                    // Locate the src attribute
                    val resp: String = img.absUrl("src")
                    Log.d("varMsg",resp)
                    val activity = activityReference.get()
                 } catch (e: InterruptedException) {
                    e.printStackTrace()
                    resp = e.message
                } catch (e: Exception) {
                    e.printStackTrace()
                    resp = e.message
                }

                return resp
            }


            override fun onPostExecute(result: String?) {

                val activity = activityReference.get()
                if (activity == null || activity.isFinishing) return
                Toast.makeText(activity, "Download Finished", Toast.LENGTH_SHORT).show()
                Log.d("varMsg",result.toString())

            }
        }
    }
}