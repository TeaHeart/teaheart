namespace SimpleAdmin.Wpf;

using System;
using System.Windows;
using Microsoft.Data.Sqlite;
using Microsoft.EntityFrameworkCore;
using Microsoft.EntityFrameworkCore.Diagnostics;
using Microsoft.Extensions.DependencyInjection;
using Microsoft.Extensions.Logging;
using SimpleAdmin.Wpf.Repositories;
using SimpleAdmin.Wpf.Services;
using SimpleAdmin.Wpf.ViewModels;
using SimpleAdmin.Wpf.Views;
using SimpleAdmin.Wpf.Windows.Markup;

public partial class App : Application
{
    public IServiceProvider Services { get; }

    public App()
    {
        var services = new ServiceCollection();
        ConfigureDbContext(services);
        ConfigureServices(services);
        ConfigureViewModels(services);
        ConfigureViews(services);
        Services = services.BuildServiceProvider();
    }

    protected override void OnStartup(StartupEventArgs e)
    {
        DiExtension.ServiceProvider = Services;
        var context = Services.GetRequiredService<AppDbContext>();
        context.Database.EnsureCreated();
        var mainWindow = Services.GetRequiredService<MainWindow>();
        mainWindow.Show();
    }

    public static new App Current => (App)Application.Current;

    private static void ConfigureViewModels(IServiceCollection services)
    {
        services.AddSingleton<MainViewModel>();
        services.AddSingleton<MenuViewModel>();
        services.AddSingleton<TabContentViewModel>();
        services.AddSingleton<LogViewModel>();
        services.AddSingleton<WelcomeViewModel>();
    }

    private static void ConfigureViews(IServiceCollection services)
    {
        services.AddTransient<MainWindow>();
        services.AddTransient<MenuView>();
        services.AddTransient<TabContentView>();
        services.AddTransient<LogView>();
        services.AddTransient<WelcomeView>();
    }

    private static void ConfigureDbContext(IServiceCollection services)
    {
        var connection = new SqliteConnection("Data Source=:memory:");
        connection.Open();
        services.AddDbContext<AppDbContext>(
            (serviceProvider, option) =>
            {
                option.UseSqlite(connection);
                var logService = serviceProvider.GetRequiredService<ILogService>();
                option
                    .EnableSensitiveDataLogging()
                    .LogTo(
                        message =>
                        {
                            logService.Log(message);
                        },
                        LogLevel.Information,
                        DbContextLoggerOptions.SingleLine
                    );
            },
            ServiceLifetime.Singleton
        );
    }

    private static void ConfigureServices(IServiceCollection services)
    {
        services.AddSingleton<ILogService, MemoryLogService>(_ => new MemoryLogService(
            LogLevel.Debug
        ));
    }
}
