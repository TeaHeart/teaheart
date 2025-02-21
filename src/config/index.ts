const env = (import.meta.env.MODE || 'prod') as keyof IEnvConfig

interface IEnvConfig {
  readonly [key: string]: {
    baseUrl: string
    mockUrl: string
  }
}

const EnvConfig: IEnvConfig = {
  dev: {
    baseUrl: '',
    mockUrl: '',
  },
  test: {
    baseUrl: '',
    mockUrl: '',
  },
  prod: {
    baseUrl: '',
    mockUrl: '',
  },
}

export default {
  env,
  mock: false,
  namespace: 'vue',
  ...EnvConfig[env],
}
