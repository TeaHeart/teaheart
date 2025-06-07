namespace SimpleAdmin.Wpf.Services;

using Microsoft.Extensions.Logging;

public interface ILogService
{
    string Logs { get; }
    void Log(string message, LogLevel logLevel = LogLevel.Information);
    void Clear();
}
