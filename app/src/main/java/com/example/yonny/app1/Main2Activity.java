package com.example.yonny.app1;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.internal.BottomNavigationItemView;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.OptionalPendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Field;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;


public class Main2Activity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,GoogleApiClient.OnConnectionFailedListener {
    private static final String TAG = Main2Activity.class.getSimpleName();

    private BottomNavigationView bottomNavigationView;
    private RecyclerView facturaList;
    private FacturaAdapter adapter;
    private List<Factura> facList;
    private TextView nameTextView;
    private TextView nameTextView1;
    private TextView emailTextView;
    private TextView idTextView;
    private int id;
    String idFace;
    private Fragment fragmentoGenerico;
    private  FragmentManager fragmentManager;
    private Button button;
    private String monto;
    private String idfactura;
    private String photo = "0";
    private ProfileTracker profileTracker;
    NavigationView navigationView;


    private GoogleApiClient googleApiClient;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

      //  facturaList = (RecyclerView) findViewById(R.id.recyclerview2);
      // facturaList.setLayoutManager(new LinearLayoutManager(this));

      // facturaList.setAdapter(new FacturaAdapter(this));
      //  FacturaAdapter adapter = new FacturaAdapter();
     //   List<Factura> perfils = FacturaRepository.getFacturas();
     //   adapter.setFactura(perfils);
    //    facturaList.setAdapter(adapter);


        profileTracker = new ProfileTracker() {
            @Override
            protected void onCurrentProfileChanged(Profile oldProfile, Profile currentProfile) {
                if (currentProfile != null) {
                    displayProfileInfo(currentProfile);
                }
            }
        };



       //  if (AccessToken.getCurrentAccessToken() == null) {
       //     goLoginScreen();
       // }



        bottomNavigationView=(BottomNavigationView)findViewById(R.id.bottomNavigationView);
        BottomNavigationViewHelper.removeShiftMode(bottomNavigationView);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();


        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);




        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                 fragmentoGenerico = null;

                 fragmentManager = getSupportFragmentManager();

                 id = item.getItemId();

                if (id == R.id.nav_camera) {

                    fragmentoGenerico=new Fragment1();
                    fragmentManager.beginTransaction().replace(R.id.contenedor,new Fragment1()).commit();


                }  else if (id == R.id.nav_slideshow) {
                    fragmentManager.beginTransaction().replace(R.id.contenedor,new Fragment3()).commit();

                    //
                }else if (id == R.id.nav_ubicacion) {
                    fragmentManager.beginTransaction().replace(R.id.contenedor,new Fragment6()).commit();

                } else if (id == R.id.nav_manage) {
                   // fragmentManager.beginTransaction().replace(R.id.contenedor,new Fragment5()).commit();

                    Fragment5 perfilFragment = new Fragment5();
                    FragmentTransaction transaction4 = getSupportFragmentManager().beginTransaction();
                    transaction4.replace(R.id.contenedor, perfilFragment);


                    Bundle args = new Bundle();
                    args.putString("photo", photo);
                    perfilFragment.setArguments(args);

                    transaction4.commit();

                } else if (id == R.id.nav_share) {
                    fragmentManager.beginTransaction().replace(R.id.contenedor,new Fragment4()).commit();



                }
                if (fragmentoGenerico != null) {
                    fragmentManager
                            .beginTransaction()
                            .replace(R.id.contenedor, fragmentoGenerico)
                            .commit();
                }

                setTitle(item.getTitle());

                return true;
            }

        });

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        googleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();


        setFragment(0);
    }


    private void removeShiftMode(BottomNavigationView bottomNavigationView) {
        BottomNavigationMenuView menuView = (BottomNavigationMenuView) bottomNavigationView.getChildAt(0);

        try {
            Field shiftingMode = menuView.getClass().getDeclaredField("mShiftingMode");
            shiftingMode.setAccessible(true);
            shiftingMode.setBoolean(menuView, false);
            shiftingMode.setAccessible(false);
            for (int i = 0; i < menuView.getChildCount(); i++) {
                BottomNavigationItemView item = (BottomNavigationItemView) menuView.getChildAt(i);
                //noinspection RestrictedApi
                item.setShiftingMode(false);
                //noinspection RestrictedApi
                item.setChecked(item.getItemData().isChecked());
            }
        } catch (NoSuchFieldException e) {
        } catch (IllegalAccessException e) {
        }
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

  @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main2, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_about) {
            new AlertDialog.Builder(this).setView(LayoutInflater.from(this).inflate(R.layout.dialog_about, null)).create().show();

            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        fragmentoGenerico = null;
        id = item.getItemId();

         fragmentManager=getSupportFragmentManager();

        if (id == R.id.nav_camera) {
           fragmentManager.beginTransaction().replace(R.id.contenedor,new Fragment1()).commit();

            // Handle the camera action
        } /*else if (id == R.id.nav_gallery) {
            fragmentManager.beginTransaction().replace(R.id.contenedor,new Fragment2()).commit();
        }*/ else if (id == R.id.nav_slideshow) {
            fragmentManager.beginTransaction().replace(R.id.contenedor,new Fragment3()).commit();
        } else if (id == R.id.nav_manage) {
            Fragment5 perfilFragment = new Fragment5();
            FragmentTransaction transaction4 = getSupportFragmentManager().beginTransaction();
            transaction4.replace(R.id.contenedor, perfilFragment);


            Bundle args = new Bundle();
            args.putString("photo", photo);
            perfilFragment.setArguments(args);

            transaction4.commit();
        }else if (id == R.id.nav_ubicacion) {
            fragmentManager.beginTransaction().replace(R.id.contenedor,new Fragment6()).commit();


        } else if (id == R.id.nav_share) {
            fragmentManager.beginTransaction().replace(R.id.contenedor,new Fragment4()).commit();
        } else if (id == R.id.nav_send) {
            LoginManager.getInstance().logOut();
            goLogInScreen();

            Auth.GoogleSignInApi.signOut(googleApiClient).setResultCallback(new ResultCallback<Status>() {
                @Override
                public void onResult(@NonNull Status status) {
                    if (status.isSuccess()) {
                        goLogInScreen();
                    } else {
                        Toast.makeText(getApplicationContext(), R.string.not_close_session, Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
        if (fragmentoGenerico != null) {
            fragmentManager
                    .beginTransaction()
                    .replace(R.id.contenedor, fragmentoGenerico)
                    .commit();
        }
        setTitle(item.getTitle());
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    @Override
    protected void onStart() {
        super.onStart();

        OptionalPendingResult<GoogleSignInResult> opr = Auth.GoogleSignInApi.silentSignIn(googleApiClient);
        if (opr.isDone()) {
            GoogleSignInResult result = opr.get();
            handleSignInResult(result);
        } else {
            opr.setResultCallback(new ResultCallback<GoogleSignInResult>() {
                @Override
                public void onResult(@NonNull GoogleSignInResult googleSignInResult) {
                    handleSignInResult(googleSignInResult);
                }
            });
        }
    }

    public void handleSignInResult(GoogleSignInResult result) {
        if (result.isSuccess() ) {

            GoogleSignInAccount account = result.getSignInAccount();
            CircleImageView photoImageView = (CircleImageView) navigationView.getHeaderView(0).findViewById(R.id.photoImageView);
            //Glide.with(getApplicationContext())
              //      .load(photoUrl)
                //    .into(photoImageView);
           // Picasso.with(this).load(account.getPhotoUrl()).into(photoImageView);

           // Glide.with(this).load(account.getPhotoUrl()).into(photoImageView);

            //idTextView.setText(account.getId());
          //  Toast.makeText(this, account.getGivenName(), Toast.LENGTH_SHORT).show();

            if(account.getPhotoUrl() != null){
                Glide.with(getApplicationContext()).load(account.getPhotoUrl()).into(photoImageView);
                photo = account.getPhotoUrl().toString();
            }

            TextView textFullname = (TextView)navigationView.getHeaderView(0).findViewById(R.id.nameTextView1);
            String valo=account.getDisplayName();
            textFullname.setText(valo);
            TextView textFullemail = (TextView) navigationView.getHeaderView(0).findViewById(R.id.emailTextView);
            String valo1=account.getEmail();
            textFullemail.setText(account.getEmail());
            String idGoogle=account.getId()  ;

            String nombres=account.getDisplayName();
            String givenname=account.getGivenName();
            SharedPreferences prefs = getSharedPreferences("MyPreferences", Context.MODE_PRIVATE);

            SharedPreferences.Editor editor = prefs.edit();

            editor.putString("nombre", valo);
            editor.putString("perfil", valo);
            editor.putString("email", valo1);
            editor.putString("idgoogle",idGoogle);
            editor.putString("idfacebook",idFace);
            editor.putString("nombres",nombres);
            editor.putString("givenname",givenname);

            editor.commit();

            enviar(idGoogle,nombres,valo1);






        }else if(AccessToken.getCurrentAccessToken()!=null){

            requestEmail(AccessToken.getCurrentAccessToken());
           // requestEmail(AccessToken.getCurrentAccessToken());

            Profile profile = Profile.getCurrentProfile();
            if (profile != null) {
                displayProfileInfo(profile);
            } else {
                Profile.fetchProfileForCurrentAccessToken();
            }

        } else {
            goLogInScreen();
        }
    }

    public interface TextListener {
        void sendText(String s);
    }

    private void goLogInScreen() {
        Intent intent = new Intent(this, Login.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }


    private void displayProfileInfo(Profile profile) {
        String name = profile.getName();
         idFace = profile.getId();
        CircleImageView photoImageView = (CircleImageView) navigationView.getHeaderView(0).findViewById(R.id.photoImageView);
        String photoUrl = profile.getProfilePictureUri(100, 100).toString();

        Glide.with(getApplicationContext())
                .load(photoUrl)
                .into(photoImageView);
        photo = profile.getProfilePictureUri(100, 100).toString();
        TextView textFullname = (TextView) navigationView.getHeaderView(0).findViewById(R.id.nameTextView1);
        textFullname.setText(name);

        Glide.with(getApplicationContext())
                .load(photo)
                .into(photoImageView);

        SharedPreferences prefs1 = getSharedPreferences("MyPreferences1", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor1 = prefs1.edit();
        editor1.putString("facebo",name);
        editor1.putString("idfac",idFace);

        enviar(idFace,name,"gmail@gamil.com");

    }


    private void requestEmail(AccessToken currentAccessToken) {
        GraphRequest request = GraphRequest.newMeRequest(currentAccessToken, new GraphRequest.GraphJSONObjectCallback() {
            @Override
            public void onCompleted(JSONObject object, GraphResponse response) {
                if (response.getError() != null) {
                    Toast.makeText(getApplicationContext(), response.getError().getErrorMessage(), Toast.LENGTH_LONG).show();
                    return;
                }
                try {
                    String email = object.getString("email");
                    setEmail(email);
                } catch (JSONException e) {
            //        Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });

        Bundle parameters = new Bundle();
        parameters.putString("fields", "id, first_name, last_name, email, gender, birthday, location");
        request.setParameters(parameters);
        request.executeAsync();
    }

    private void setEmail(String email) {
        TextView textFullemail = (TextView) navigationView.getHeaderView(0).findViewById(R.id.emailTextView);
        textFullemail.setText(email);

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
    public void setFragment(int position) {
        FragmentManager fragmentManager;
        FragmentTransaction fragmentTransaction;
        switch (position) {
            case 0:
                fragmentManager = getSupportFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                Fragment1 inboxFragment = new Fragment1();
                fragmentTransaction.replace(R.id.contenedor, inboxFragment);
                fragmentTransaction.commit();
                break;

            case 1:
                fragmentManager = getSupportFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                Fragment2 starredFragment = new Fragment2();
                fragmentTransaction.replace(R.id.contenedor,starredFragment);
                fragmentTransaction.commit();
                break;
        }
    }

    public void enviar(final String id_usuario , String nombres,String email) {
        final String idUsuario=id_usuario.toString();
        final String nombUsu=nombres.toString();
        final String corrUsu=email.toString();


        com.android.volley.Response.Listener<String> responseListener=new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try{

                    JSONObject jsonResponse=new JSONObject(response);
                    JSONArray success=jsonResponse.getJSONArray("message");

                    for (int i = 0; i < success.length(); i++) {
                        JSONObject Jasonobject = null;
                         Jasonobject = success.getJSONObject(i);
                         // monto = Jasonobject.getString("monto");
                         // idfactura = Jasonobject.getString("idfactura");


                    }


                }catch(JSONException e){
                    e.printStackTrace();
                }

            }

        };
        UsuarioRequest usuarioRequest=new UsuarioRequest(idUsuario,nombUsu,corrUsu,responseListener);
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(usuarioRequest);


    }
}



class BottomNavigationViewHelper {

    static void removeShiftMode(BottomNavigationView view) {
        BottomNavigationMenuView menuView = (BottomNavigationMenuView) view.getChildAt(0);
        try {
            Field shiftingMode = menuView.getClass().getDeclaredField("mShiftingMode");
            shiftingMode.setAccessible(true);
            shiftingMode.setBoolean(menuView, false);
            shiftingMode.setAccessible(false);
            for (int i = 0; i < menuView.getChildCount(); i++) {
                BottomNavigationItemView item = (BottomNavigationItemView) menuView.getChildAt(i);
                item.setShiftingMode(false);
                // set once again checked value, so view will be updated
                item.setChecked(item.getItemData().isChecked());
            }
        } catch (NoSuchFieldException e) {
            Log.e("ERROR NO SUCH FIELD", "Unable to get shift mode field");
        } catch (IllegalAccessException e) {
            Log.e("ERROR ILLEGAL ALG", "Unable to change value of shift mode");
        }
    }


}