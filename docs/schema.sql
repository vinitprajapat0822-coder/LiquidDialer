-- LiquidDialer Room schema (SQLite)
-- All local. No multi-user RLS needed.

CREATE TABLE IF NOT EXISTS contacts (
  id TEXT PRIMARY KEY NOT NULL,
  display_name TEXT NOT NULL,
  photo_uri TEXT,
  is_favourite INTEGER NOT NULL DEFAULT 0,
  speed_dial_slot INTEGER,
  custom_ringtone_uri TEXT,
  notes TEXT,
  updated_at INTEGER NOT NULL
);
CREATE INDEX IF NOT EXISTS idx_contacts_name ON contacts(display_name);
CREATE INDEX IF NOT EXISTS idx_contacts_speed ON contacts(speed_dial_slot);

CREATE TABLE IF NOT EXISTS phone_numbers (
  id INTEGER PRIMARY KEY AUTOINCREMENT,
  contact_id TEXT NOT NULL,
  number TEXT NOT NULL,
  type TEXT,
  is_primary INTEGER NOT NULL DEFAULT 0,
  FOREIGN KEY(contact_id) REFERENCES contacts(id) ON DELETE CASCADE
);
CREATE INDEX IF NOT EXISTS idx_phones_number ON phone_numbers(number);
CREATE INDEX IF NOT EXISTS idx_phones_contact ON phone_numbers(contact_id);

CREATE TABLE IF NOT EXISTS call_log (
  id INTEGER PRIMARY KEY AUTOINCREMENT,
  system_id INTEGER,
  contact_id TEXT,
  number TEXT NOT NULL,
  type INTEGER NOT NULL,
  timestamp INTEGER NOT NULL,
  duration INTEGER NOT NULL DEFAULT 0,
  sim_slot INTEGER,
  is_spam INTEGER NOT NULL DEFAULT 0,
  spam_label TEXT,
  FOREIGN KEY(contact_id) REFERENCES contacts(id) ON DELETE SET NULL
);
CREATE INDEX IF NOT EXISTS idx_calllog_ts ON call_log(timestamp DESC);
CREATE INDEX IF NOT EXISTS idx_calllog_number ON call_log(number);

CREATE TABLE IF NOT EXISTS blocked_numbers (
  number TEXT PRIMARY KEY NOT NULL,
  reason TEXT,
  blocked_at INTEGER NOT NULL
);

CREATE TABLE IF NOT EXISTS themes (
  id TEXT PRIMARY KEY NOT NULL,
  name TEXT NOT NULL,
  preview_asset TEXT,
  background_asset TEXT,
  button_style TEXT,
  blur_radius REAL NOT NULL DEFAULT 24.0,
  tint_color INTEGER,
  is_dark INTEGER NOT NULL DEFAULT 1
);

CREATE TABLE IF NOT EXISTS settings (
  id INTEGER PRIMARY KEY CHECK (id = 1),
  current_theme_id TEXT,
  default_sim_preference INTEGER,
  slide_to_answer INTEGER NOT NULL DEFAULT 1,
  flash_enabled INTEGER NOT NULL DEFAULT 0,
  flash_pattern TEXT DEFAULT 'default',
  announcer_enabled INTEGER NOT NULL DEFAULT 0,
  language TEXT DEFAULT 'en',
  dialer_light INTEGER NOT NULL DEFAULT 0,
  quick_replies TEXT,
  FOREIGN KEY(current_theme_id) REFERENCES themes(id)
);

CREATE TABLE IF NOT EXISTS fake_calls (
  id INTEGER PRIMARY KEY AUTOINCREMENT,
  name TEXT NOT NULL,
  number TEXT,
  trigger_at INTEGER NOT NULL,
  ringtone_uri TEXT,
  status TEXT NOT NULL DEFAULT 'scheduled'
);
