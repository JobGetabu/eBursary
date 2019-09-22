package com.job.ebursary.fragments


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.job.ebursary.R
import com.job.ebursary.model.ApplicationModel
import kotlinx.android.synthetic.main.fragment_my_application.*


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class MyApplicationFragment : Fragment() {

    private lateinit var adapter: FirestoreRecyclerAdapter<ApplicationModel, BursaryViewHolder>

    private var auth = FirebaseAuth.getInstance()
    private var firestore = FirebaseFirestore.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_my_application, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        setupMyList()
    }

    private fun setupMyList() {

        val query = FirebaseFirestore.getInstance().collection("applications")
            .whereEqualTo("userid", auth.currentUser!!.uid)


        val options = FirestoreRecyclerOptions.Builder<ApplicationModel>()
            .setQuery(query,ApplicationModel::class.java)
            .build()

        adapter = object : FirestoreRecyclerAdapter<ApplicationModel, BursaryViewHolder>(options) {
            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BursaryViewHolder {

                val vi = LayoutInflater.from(context)
                val v = vi.inflate(R.layout.single_application, parent, false) as CardView

                return BursaryViewHolder(v)
            }

            override fun onBindViewHolder(p0: BursaryViewHolder, p1: Int, p2: ApplicationModel) {

            }

            override fun onError(e: FirebaseFirestoreException) {
                super.onError(e)
            }

        }

        adapter.startListening()
        adapter.notifyDataSetChanged()
        applist.adapter = adapter

    }

    override fun onStart() {
        super.onStart()
        if (adapter != null)
            adapter.startListening()
    }

    override fun onStop() {
        super.onStop()
        if (adapter != null)
            adapter.stopListening()
    }

}
