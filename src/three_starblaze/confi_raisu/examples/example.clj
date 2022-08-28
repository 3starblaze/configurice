(ns three-starblaze.confi-raisu.examples.example)

(def dunst-config
  {"global"
   {"monitor" 0
    "follow" "mouse"
    "geometry" "400x5-30+20"
    "indicate_hidden" "yes"
    "shrink" "no"
    "transparency" 0
    "notification_height" 0
    "padding" 32
    "horizontal_padding" 32
    "frame_width" 1
    "frame_color" "#aaaaaa"
    "separator_color" "frame"
    "sort" "yes"
    "idle_threshold" "120"
    "font" "Hack 10"
    "line_height" 0
    "markup" "full"
    "format" "<span font_desc='Hack 14'><b>%s</b></span>\n<i>%b</i>"
    "alignment" "left"
    "show_age_threshold" 60
    "word_wrap" "yes"
    "ellipsize" "middle"
    "ignore_newline" "no"
    "stack_duplicates" false
    "hide_duplicate_count" false
    "show_indicators" "yes"
    "icon_position" "off"
    "max_icon_size" 32
    "icon_path" "/usr/share/icons/gnome/16x16/status/:/usr/share/icons/gnome/16x16/devices/"
    "sticky_history" "yes"
    "history_length" 20
    "dmenu" "/usr/bin/dmenu -p dunst:"
    "browser" "/usr/bin/sensible-browser"
    "always_run_script" true
    "title" "Dunst"
    "class" "Dunst"
    "startup_notification" false
    "verbosity" "mesg"
    "corner_radius" 0
    "mouse_left_click" "close_current"
    "mouse_middle_click" "do_action"
    "mouse_right_click" "close_all"}

   "shortcuts"
   {"close" "ctrl+space"
    "close_all" "ctrl+shift+space"
    "history" "ctrl+grave"
    "context" "ctrl+shift+period"}

   "urgency_low"
   {"background" "#222222"
    "foreground" "#888888"
    "timeout" 10}

   "urgency_normal"
   {"background" "#282c34"
    "foreground" "#ABB2BF"
    "timeout" 10}

   "urgency_critical"
   {"background" "#900000"
    "foreground" "#ffffff"
    "timeout" 0}})

;; %s is for generated config path
(def dunst-command "dunst -config %s")

(def dunst-data
  {:key "dunst"
   :config dunst-config
   :command dunst-command})
