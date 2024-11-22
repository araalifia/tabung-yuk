package com.naveenapps.expensemanager.feature.dashboard;

import com.naveenapps.expensemanager.core.common.utils.AppCoroutineDispatchers;
import com.naveenapps.expensemanager.core.domain.usecase.account.GetAllAccountsUseCase;
import com.naveenapps.expensemanager.core.domain.usecase.budget.GetBudgetsUseCase;
import com.naveenapps.expensemanager.core.domain.usecase.settings.currency.GetCurrencyUseCase;
import com.naveenapps.expensemanager.core.domain.usecase.settings.currency.GetFormattedAmountUseCase;
import com.naveenapps.expensemanager.core.domain.usecase.transaction.GetTransactionGroupByCategoryUseCase;
import com.naveenapps.expensemanager.core.domain.usecase.transaction.GetTransactionWithFilterUseCase;
import com.naveenapps.expensemanager.core.navigation.AppComposeNavigator;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata
@QualifierMetadata
@DaggerGenerated
@Generated(
    value = "dagger.internal.codegen.ComponentProcessor",
    comments = "https://dagger.dev"
)
@SuppressWarnings({
    "unchecked",
    "rawtypes",
    "KotlinInternal",
    "KotlinInternalInJava",
    "cast"
})
public final class DashboardViewModel_Factory implements Factory<DashboardViewModel> {
  private final Provider<GetTransactionWithFilterUseCase> getTransactionWithFilterUseCaseProvider;

  private final Provider<GetCurrencyUseCase> getCurrencyUseCaseProvider;

  private final Provider<GetFormattedAmountUseCase> getFormattedAmountUseCaseProvider;

  private final Provider<GetAllAccountsUseCase> getAllAccountsUseCaseProvider;

  private final Provider<GetTransactionGroupByCategoryUseCase> getTransactionGroupByCategoryUseCaseProvider;

  private final Provider<GetBudgetsUseCase> getBudgetsUseCaseProvider;

  private final Provider<AppCoroutineDispatchers> appCoroutineDispatchersProvider;

  private final Provider<AppComposeNavigator> appComposeNavigatorProvider;

  public DashboardViewModel_Factory(
      Provider<GetTransactionWithFilterUseCase> getTransactionWithFilterUseCaseProvider,
      Provider<GetCurrencyUseCase> getCurrencyUseCaseProvider,
      Provider<GetFormattedAmountUseCase> getFormattedAmountUseCaseProvider,
      Provider<GetAllAccountsUseCase> getAllAccountsUseCaseProvider,
      Provider<GetTransactionGroupByCategoryUseCase> getTransactionGroupByCategoryUseCaseProvider,
      Provider<GetBudgetsUseCase> getBudgetsUseCaseProvider,
      Provider<AppCoroutineDispatchers> appCoroutineDispatchersProvider,
      Provider<AppComposeNavigator> appComposeNavigatorProvider) {
    this.getTransactionWithFilterUseCaseProvider = getTransactionWithFilterUseCaseProvider;
    this.getCurrencyUseCaseProvider = getCurrencyUseCaseProvider;
    this.getFormattedAmountUseCaseProvider = getFormattedAmountUseCaseProvider;
    this.getAllAccountsUseCaseProvider = getAllAccountsUseCaseProvider;
    this.getTransactionGroupByCategoryUseCaseProvider = getTransactionGroupByCategoryUseCaseProvider;
    this.getBudgetsUseCaseProvider = getBudgetsUseCaseProvider;
    this.appCoroutineDispatchersProvider = appCoroutineDispatchersProvider;
    this.appComposeNavigatorProvider = appComposeNavigatorProvider;
  }

  @Override
  public DashboardViewModel get() {
    return newInstance(getTransactionWithFilterUseCaseProvider.get(), getCurrencyUseCaseProvider.get(), getFormattedAmountUseCaseProvider.get(), getAllAccountsUseCaseProvider.get(), getTransactionGroupByCategoryUseCaseProvider.get(), getBudgetsUseCaseProvider.get(), appCoroutineDispatchersProvider.get(), appComposeNavigatorProvider.get());
  }

  public static DashboardViewModel_Factory create(
      Provider<GetTransactionWithFilterUseCase> getTransactionWithFilterUseCaseProvider,
      Provider<GetCurrencyUseCase> getCurrencyUseCaseProvider,
      Provider<GetFormattedAmountUseCase> getFormattedAmountUseCaseProvider,
      Provider<GetAllAccountsUseCase> getAllAccountsUseCaseProvider,
      Provider<GetTransactionGroupByCategoryUseCase> getTransactionGroupByCategoryUseCaseProvider,
      Provider<GetBudgetsUseCase> getBudgetsUseCaseProvider,
      Provider<AppCoroutineDispatchers> appCoroutineDispatchersProvider,
      Provider<AppComposeNavigator> appComposeNavigatorProvider) {
    return new DashboardViewModel_Factory(getTransactionWithFilterUseCaseProvider, getCurrencyUseCaseProvider, getFormattedAmountUseCaseProvider, getAllAccountsUseCaseProvider, getTransactionGroupByCategoryUseCaseProvider, getBudgetsUseCaseProvider, appCoroutineDispatchersProvider, appComposeNavigatorProvider);
  }

  public static DashboardViewModel newInstance(
      GetTransactionWithFilterUseCase getTransactionWithFilterUseCase,
      GetCurrencyUseCase getCurrencyUseCase, GetFormattedAmountUseCase getFormattedAmountUseCase,
      GetAllAccountsUseCase getAllAccountsUseCase,
      GetTransactionGroupByCategoryUseCase getTransactionGroupByCategoryUseCase,
      GetBudgetsUseCase getBudgetsUseCase, AppCoroutineDispatchers appCoroutineDispatchers,
      AppComposeNavigator appComposeNavigator) {
    return new DashboardViewModel(getTransactionWithFilterUseCase, getCurrencyUseCase, getFormattedAmountUseCase, getAllAccountsUseCase, getTransactionGroupByCategoryUseCase, getBudgetsUseCase, appCoroutineDispatchers, appComposeNavigator);
  }
}
