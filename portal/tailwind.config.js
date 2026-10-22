/** @type {import('tailwindcss').Config} */
export default {
  // 这里配置了所有可能包含 Tailwind 类名的文件路径
  content: [
    "./index.html",
    "./src/**/*.{vue,js,ts,jsx,tsx}",
  ],
  theme: {
    extend: {
      // 你可以在这里自定义颜色，比如定义你们公司的“科技蓝”
      colors: {
        'nova-blue': '#0056b3', 
      }
    },
  },
  plugins: [],
}