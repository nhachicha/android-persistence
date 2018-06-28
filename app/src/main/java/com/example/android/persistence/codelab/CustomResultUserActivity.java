/*
 * Copyright 2017, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.persistence.codelab;

import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.android.codelabs.persistence.R;
import com.example.android.codelabs.persistence.databinding.DbActivityBinding;


public class CustomResultUserActivity extends AppCompatActivity {

    private CustomResultViewModel mShowUserViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        DbActivityBinding viewDataBinding = DataBindingUtil.setContentView(this, R.layout.db_activity);
        viewDataBinding.setLifecycleOwner(this);

        // Android will instantiate my ViewModel for me, and the best part is
        // the viewModel will survive configurationChanges!
        mShowUserViewModel = ViewModelProviders.of(this).get(CustomResultViewModel.class);

        viewDataBinding.setViewmodel(mShowUserViewModel);
    }

    public void onRefreshBtClicked(View view) {
        mShowUserViewModel.simulateDataUpdates();
    }
}
