namespace SimpleAdmin.Wpf.Services;

using System;
using System.Diagnostics;
using System.Text;
using System.Threading;
using CommunityToolkit.Mvvm.Messaging;
using Microsoft.Extensions.Logging;
using SimpleAdmin.Wpf.Services.Messages;

public class MemoryLogService : ILogService
{
    public string Logs => logBuilder.ToString();
    private readonly StringBuilder logBuilder = new();
    private readonly LogLevel logLevel;

    public MemoryLogService(LogLevel logLevel)
    {
        this.logLevel = logLevel;
    }

    public void Clear()
    {
        logBuilder.Clear();
        SendLogUpdatedMessage();
    }

    public void Log(string message, LogLevel logLevel = LogLevel.Information)
    {
        if (logLevel < this.logLevel)
        {
            return;
        }

        var time = DateTime.Now.ToString("yyyy-MM-dd HH:mm:ss.fff");
        var stack = new StackTrace();
        var frame = stack.GetFrame(1);
        var threadName = Thread.CurrentThread.Name ?? "Main";
        string fullTypeName = frame?.GetMethod()?.DeclaringType?.FullName ?? "UnknownType";
        string methodName = frame?.GetMethod()?.Name ?? "UnknownMethod";

        var msg = $"{time} {logLevel} - [{threadName}] {fullTypeName}.{methodName} : {message}";
        logBuilder.AppendLine(msg);

        SendLogUpdatedMessage();
    }

    private void SendLogUpdatedMessage()
    {
        WeakReferenceMessenger.Default.Send(new LogUpdatedMessage(Logs));
    }
}
