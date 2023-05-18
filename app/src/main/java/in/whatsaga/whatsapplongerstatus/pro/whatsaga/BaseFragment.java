package in.whatsaga.whatsapplongerstatus.pro.whatsaga;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelStoreOwner;

public abstract class BaseFragment<Binding extends ViewDataBinding> extends Fragment {

    protected Binding binding;
    protected Activity activity;

    public BaseFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, getLayoutId(), container, false);
        activity = requireActivity();
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        onReady();
    }

    protected ViewModelStoreOwner getViewModelStoreOwner() {
        return this;
    }

    protected abstract int getLayoutId();

    protected abstract void onReady();
}